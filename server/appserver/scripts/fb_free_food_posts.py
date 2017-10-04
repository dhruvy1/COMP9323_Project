import facebook
import json
import re
import requests
from sys import argv

# set access token and version of fb api
graph = facebook.GraphAPI(
    access_token="425065081221660|Z14Prd648CGC7pkGiqkKBTyfrDk", version=2.10)


def getFreeFoodPosts(name, result_limit):

    # set the event fields that we will need
    post_fields = "id, message, updated_time, link"
    # get page events, providing page name, event fields and the result limit
    posts_dict = graph.get_connections(
        id=name, fields=post_fields, connection_name="feed", limit=result_limit)

    for post in posts_dict["data"]:
        if re.search(r'Free', post["message"], re.M | re.I):
            payload = {
                "post_id": post["id"],
                "message": post["message"],
                "updated_date": str(getDateFromTime(post["updated_time"])),
                "updated_time": str(filterTime(post["updated_time"])),
            }
            payload["post_id"] += "copy"
            if "link" in post:
                if re.search(r"photo.php", post["link"], re.M | re.I):
                    payload["photo_link"] = getPhotoSrc(post["link"])
                elif re.search(r"events", post["link"], re.M | re.I):
                    payload["event_link"] = post["link"]
            payload["created_by"] = "Facebook"
            
            # print(post)
            # print(payload)
            postToRestServer(payload)


def getDateFromTime(time):
    return re.sub(r"T.*", "", time)


def filterTime(time):
    return re.sub(r"T.*", "", re.sub(r"^.*T", "", time))

def getPhotoSrc(photo_link):
    fb_id = re.match(r".*fbid=(.*?)&.*", photo_link)
    album = graph.get_object(id=fb_id.group(1), fields="images")
    return album["images"][0]["source"]


def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/food_deals/"
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)


if __name__ == "__main__":
    getFreeFoodPosts("428460270539887", 10)
