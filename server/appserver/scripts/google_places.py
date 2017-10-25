from datetime import datetime
import googlemaps
import json
import requests
import time


# api key of google maps
API_KEY = "AIzaSyCkWimLuZg74e53MSlegLb3ek65bR6I8Qw"
gmaps = googlemaps.Client(key=API_KEY)


# get all the food places around UNSW
def getFoodPlacesNearUNSW(next_page_token):
    # Request food places near UNSW
    unsw_coordinates = "-33.9173425, 151.2290788"

    # get the food places near UNSW using the API and place the results in places_nearby_results
    # we only want food places and 1000km around UNSW
    places_nearby_results = gmaps.places_nearby(
        unsw_coordinates, 1000, type="food", page_token=next_page_token)

    # loop through all the results of the food places around UNSW
    for place in places_nearby_results["results"]:
        # generate a payload to be sent over to our server
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
        payload["created_by"] = "Google"

        postToRestServer(payload)  # post the generated payload to the server

    # get the next set of food places around UNSW
    if "next_page_token" in places_nearby_results:
        time.sleep(1.5)
        getFoodPlacesNearUNSW(places_nearby_results["next_page_token"])


# get the photo associated with the food place
def getGooglePhotoUrl(photo):
    url = "https://maps.googleapis.com/maps/api/place/photo?photoreference="
    url += photo["photo_reference"]
    url += "&maxheight=" + str(photo["height"]) + \
        "&maxwidth=" + str(photo["width"])
    url += "&key=" + API_KEY
    return url


def postToRestServer(payload):
    url = "http://52.65.129.3:8000/api/food_places/"  # url of the server to post
    # http headers
    headers = {"Content-Type": "application/json",
               "Accept": "application/json"}
    # post the payload to the server and capture the response
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    print(response)  # print the response to the console


if __name__ == "__main__":
    getFoodPlacesNearUNSW("")
