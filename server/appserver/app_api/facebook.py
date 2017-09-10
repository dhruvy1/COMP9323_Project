import facebook
import re
from .models import *


def getFacebookEvents(page_name, result_limit):
    if not page_name.strip():
        return

    # set access token and version of fb api
    graph = facebook.GraphAPI(
        access_token="425065081221660|Z14Prd648CGC7pkGiqkKBTyfrDk", version=2.10)

    # set the event fields that we will need
    event_fields = "id, name, description, start_time, end_time, place, cover"
    # get page events, providing page name, event fields and the result limit
    page_events = graph.get_connections(
        id=page_name, fields=event_fields, connection_name="events", limit=result_limit)

    my_event_list = []
    for fb_event in page_events["data"]:
        my_event = Event()  # create default constructed event object
        my_event.facebook_id = fb_event["id"]
        my_event.name = fb_event["name"]
        my_event.description = fb_event["description"]

        my_event.start_date = re.sub(r"T.*", "", fb_event["start_time"])
        my_event.end_date = re.sub(r"T.*", "", fb_event["end_time"])
        my_event.start_time = re.sub(r"^.*T", "", fb_event["start_time"])
        my_event.end_time = re.sub(r"^.*T", "", fb_event["end_time"])

        # need to use if statements because fb events allow place of event to be optional
        if "place" in fb_event:
            fb_event_place = fb_event["place"]  # alias for place
            my_event.place_name = fb_event_place["name"]
            if "location" in fb_event_place:
                # alias for place location
                fb_event_location = fb_event_place["location"]
                if "city" in fb_event_location:
                    my_event.city = fb_event_location["city"]
                if "country" in fb_event_location:
                    my_event.country = fb_event_location["country"]
                if "latitude" in fb_event_location:
                    my_event.latitude = fb_event_location["latitude"]
                if "longitude" in fb_event_location:
                    my_event.longitude = fb_event_location["longitude"]
                if "state" in fb_event_location:
                    my_event.state = fb_event_location["state"]
                if "street" in fb_event_location:
                    my_event.street = fb_event_location["street"]
                if "zip" in fb_event_location:
                    my_event.zip = fb_event_location["zip"]

        # cover photos can be optional in fb events
        if "cover" in fb_event:
            my_event.cover_id = fb_event["cover"]["id"]
            my_event.source_url = fb_event["cover"]["source"]

        my_event_list.append(my_event)

    return my_event_list  # returns a list of event objects
