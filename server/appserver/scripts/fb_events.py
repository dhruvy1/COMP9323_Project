import facebook
import json
import re
import requests
from sys import argv

# set access token and version of fb api
graph = facebook.GraphAPI(
    access_token="425065081221660|Z14Prd648CGC7pkGiqkKBTyfrDk", version=2.10)


# get the facebook events
# @name the name of the page
# @result_limit the number of events to return from the event
def getPageEvents(name, result_limit):
    # check if the arguments are supplied correctly
    if not name.strip() or result_limit < 1:
        print("You supplied an empty page name or limit < 1")
        return

    # set the event fields that we will need to be returned in the facebook response json
    event_fields = "id, name, description, start_time, end_time, place, cover"

    # get page events, providing page name, event fields and the result limit
    events_dict = graph.get_connections(
        id=name, fields=event_fields, connection_name="events", limit=result_limit)

    # loop through all the events returned by facebook
    for event in events_dict["data"]:
        # generate a payload to be sent over to our server
        payload = {
            "facebook_id": event["id"],
            "name": event["name"],
            "start_date": str(getDateFromTime(event["start_time"])),
            "start_time": str(filterTime(event["start_time"])),
        }
        if "end_time" in event:
            payload["end_date"] = str(getDateFromTime(event["end_time"]))
            payload["end_time"] = str(filterTime(event["end_time"]))
        if "description" in event:
            payload["description"] = event["description"]
        if "place" in event:
            payload["place_name"] = event["place"]["name"]
            if "location" in event["place"]:
                if "city" in event["place"]["location"]:
                    payload["city"] = event["place"]["location"]["city"]
                if "country" in event["place"]["location"]:
                    payload["country"] = event["place"]["location"]["country"]
                if "latitude" in event["place"]["location"]:
                    payload["latitude"] = event["place"]["location"]["latitude"]
                if "longitude" in event["place"]["location"]:
                    payload["longitude"] = event["place"]["location"]["longitude"]
                if "state" in event["place"]["location"]:
                    payload["state"] = event["place"]["location"]["state"]
                if "street" in event["place"]["location"]:
                    payload["street"] = event["place"]["location"]["street"]
                if "zip" in event["place"]["location"]:
                    payload["zip"] = event["place"]["location"]["zip"]
        if "cover" in event:
            payload["cover_id"] = event["cover"]["id"]
            payload["source_url"] = event["cover"]["source"]
        payload["created_by"] = "Facebook"

        postToRestServer(payload)  # post the generated payload to the server


# filter the facebook date into our server format
def getDateFromTime(time):
    return re.sub(r"T.*", "", time)


# filter the facebook time into our server format
def filterTime(time):
    return re.sub(r"T.*", "", re.sub(r"^.*T", "", time))


# post to our server
def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/events/"  # url of the server to post
    # http headers
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    # post the payload to the server and capture the response
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)  # print the response to the console


if __name__ == "__main__":
    arg_len = len(argv)
    # checking user input to ensure that the right arguments are supplied
    if (arg_len <= 1):  # no arguments supplied
        print("Input: python3 " + argv[0] + " [limit] <page_name, ...>")
        print("[limit]: Optional. Default is 10. Value must be greater than 0.")
        print("<page_name, ...>: Required. Handles multiple page names at once.")
        print("Sample input: python3 " + argv[0] + " 5 ArcUNSW")
        print("Note: Pages can use page_name, Groups need group_id.")
    if (arg_len == 2):  # only 1 argument supplied
        if not argv[1].isdigit():  # limit not supplied
            getPageEvents(argv[1], 10)
        else:
            print("You did not supply any page_names")
    if (arg_len > 2):  # more than 2 argumments supplied
        if (not argv[1].isdigit()):  # limit not supplied
            for page_name in argv:
                getPageEvents(page_name, 10)
        else:  # limit supplied
            for i in range(2, arg_len):
                getPageEvents(argv[i], int(argv[1]))
