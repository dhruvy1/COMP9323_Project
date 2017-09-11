from rest_framework import serializers
from .models import *


class MobileUserCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = MobileUser
        fields = ['username', 'device_id']


class MobileUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = MobileUser
        fields = ['id', 'username', 'device_id']


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ['id', 'name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url']


class EventCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ['name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url']


class FoodDealSerializer(serializers.ModelSerializer):
    class Meta:
        fields = ['id', 'post_id', 'message', 'updated_time', 'photo_link', 'event_link']


class FoodDealCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        fields = ['post_id', 'message', 'updated_time', 'photo_link', 'event_link']


