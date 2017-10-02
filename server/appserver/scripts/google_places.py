from datetime import datetime
import googlemaps
import json
import requests
import time

API_KEY = "AIzaSyCkWimLuZg74e53MSlegLb3ek65bR6I8Qw"
gmaps = googlemaps.Client(key=API_KEY)


def getFoodPlacesNearUNSW(next_page_token):
    # Request places near UNSW
    unsw_coordinates = "-33.9173425, 151.2290788"
    places_nearby_results = gmaps.places_nearby(
        unsw_coordinates, 1000, type="food", page_token=next_page_token)

    for place in places_nearby_results["results"]:
        payload = {
            "name": place["name"],
            "location": place["vicinity"],
            "latitude": str(place["geometry"]["location"]["lat"]),
            "longitude": str(place["geometry"]["location"]["lng"])
        }
        if "price_level" in place:
            payload["price_level"] = str(place["price_level"])
        if "rating" in place:
            payload["google_rating"] = str(place["rating"])
        if "photos" in place:
            payload["photo"] = getGooglePhotoUrl(place["photos"][0])
        postToRestServer(payload)
        # print(payload)
        # print(places_nearby_results)
    if "next_page_token" in places_nearby_results:
        time.sleep(1.5)
        getFoodPlacesNearUNSW(places_nearby_results["next_page_token"])


def getGooglePhotoUrl(photo):
    url = "https://maps.googleapis.com/maps/api/place/photo?photoreference="
    url += photo["photo_reference"]
    url += "&maxheight=" + str(photo["height"]) + "&maxwidth=" + str(photo["width"])
    url += "&key=" + API_KEY
    return url


def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/food_places/"
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)


if __name__ == "__main__":
    getFoodPlacesNearUNSW("")
