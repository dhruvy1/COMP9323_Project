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
        fields = ['name', 'description', 'start_time', 'facebook_id', 'end_time', 'place']

        depth = 2


class PlaceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Place
        fields = ['name', 'location']
        depth = 2


class LocationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Location
        fields = ['city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip']
