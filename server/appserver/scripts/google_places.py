from datetime import datetime
import googlemaps
import json
import requests
import time

gmaps = googlemaps.Client(key='AIzaSyCkWimLuZg74e53MSlegLb3ek65bR6I8Qw')


def getFoodPlacesNearUNSW(next_page_token):
    # Request places near UNSW
    unsw_coordinates = "-33.9173425, 151.2290788"
    places_nearby_results = gmaps.places_nearby(
        unsw_coordinates, 1000, type="food", page_token=next_page_token)
    for place in places_nearby_results["results"]:
        payload = {
            "name": place["name"],
            "location": place["vicinity"],
            "latitude": place["geometry"]["location"]["lat"],
            "longitutde": place["geometry"]["location"]["lng"]
        }
        if "price_level" in place:
            payload["price_level"] = place["price_level"]
        if "rating" in place:
            payload["google_rating"] = place["rating"]
        # postToRestServer(payload)
        print(payload)
        # print(places_nearby_results)
    if "next_page_token" in places_nearby_results:
        time.sleep(1.5)
        getFoodPlacesNearUNSW(places_nearby_results["next_page_token"])


def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/food_deals/"
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)


if __name__ == "__main__":
    getFoodPlacesNearUNSW("")
