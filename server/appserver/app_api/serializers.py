from rest_framework import serializers
from .models import *


class MobileUserCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = MobileUser
        fields = ['username', 'device_id', 'karma_points']


class MobileUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = MobileUser
        fields = ['id', 'username', 'device_id', 'karma_points']


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ['id', 'name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url', 'rating']


class EventCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ['name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url', 'rating']


class FoodDealSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodDeal
        fields = ['id', 'post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating']


class FoodDealCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodDeal
        fields = ['post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating']


# Food Place
class FoodPlaceSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['id', 'name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link', 'rating']


class FoodPlaceCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link', 'rating']
