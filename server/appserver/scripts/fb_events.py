import facebook
import json
import re
import requests
from sys import argv

# set access token and version of fb api
graph = facebook.GraphAPI(
    access_token="EAACEdEose0cBAJMpqJZCgQbfltIrbyqkZCzBNFLAslBunngku9o7x7tlIDif7OtRZAXhfRetJs3UW7mDMeONNnxNdDr55LgSmLGSzBwUkkBQ62ZCCvROpVkVzuw3Jq8l21sErk4y71DmQTTfmwVrnkGfup2nt7poL2CI6IZC4hwoQhMSTcTCLbQZAPJvxKq70ZD", version=2.10)


def getPageEvents(name, result_limit):
    if not name.strip() or result_limit < 1:
        print("You supplied an empty page name or limit < 1")
        return

    # set the event fields that we will need
    event_fields = "id, name, description, start_time, end_time, place, cover"
    # get page events, providing page name, event fields and the result limit
    events_dict = graph.get_connections(
        id=name, fields=event_fields, connection_name="events", limit=result_limit)

    for event in events_dict["data"]:
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

        postToRestServer(payload)


def getDateFromTime(time):
    return re.sub(r"T.*", "", time)


def filterTime(time):
    return re.sub(r"T.*", "", re.sub(r"^.*T", "", time))


def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/events/"
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)


if __name__ == "__main__":
    arg_len = len(argv)
    if (arg_len <= 1):
        print("Input: python3 " + argv[0] + " [limit] <page_name, ...>")
        print("[limit]: Optional. Default is 10. Value must be greater than 0.")
        print("<page_name, ...>: Required. Handles multiple page names at once.")
        print("Sample input: python3 " +
              argv[0] + " 5 ArcUNSW 428460270539887")
        print("Note: Pages can use page_name, Groups need group_id.")
    if (arg_len == 2):
        if (not argv[1].isdigit()):
            print("XD")
        else:
            print("You did not supply any page_names")
    if (arg_len > 2):
        if (not argv[1].isdigit()):  # limit not supplied
            for page_name in argv:
                getPageEvents(page_name, 10)
        else:  # limit supplied
            for i in range(2, arg_len):
                getPageEvents(argv[i], int(argv[1]))
