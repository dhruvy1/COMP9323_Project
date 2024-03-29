import facebook
import json
import re
import requests
from sys import argv

# set access token and version of fb api
graph = facebook.GraphAPI(
    access_token="425065081221660|Z14Prd648CGC7pkGiqkKBTyfrDk", version=2.10)


# get the facebook free page posts
# @name the name of the page
# @result_limit the number of events to return from the event
def getFreeFoodPosts(name, result_limit):

    # set the event fields that we will need
    post_fields = "id, message, updated_time, link"

    # get page events, providing page name, event fields and the result limit
    posts_dict = graph.get_connections(
        id=name, fields=post_fields, connection_name="feed", limit=result_limit)

    # loop through all the posts returned by facebook
    for post in posts_dict["data"]:
        # only capture the posts that have the word 'free' case insensitive
        if re.search(r'Free', post["message"], re.M | re.I):
            # generate a payload to be sent over to our server
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

            # post the generated payload to the server
            postToRestServer(payload)


# filter the facebook date into our server format
def getDateFromTime(time):
    return re.sub(r"T.*", "", time)


# filter the facebook time into our server format
def filterTime(time):
    return re.sub(r"T.*", "", re.sub(r"^.*T", "", time))


# get the image src link of the food post
def getPhotoSrc(photo_link):
    fb_id = re.match(r".*fbid=(.*?)&.*", photo_link)
    album = graph.get_object(id=fb_id.group(1), fields="images")
    return album["images"][0]["source"]


# post to our server
def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/food_deals/"  # url of the server to post
    # http headers
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    # post the payload to the server and capture the response
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)  # print the response to the console


if __name__ == "__main__":
    getFreeFoodPosts("428460270539887", 10)
