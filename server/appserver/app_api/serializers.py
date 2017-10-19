from rest_framework import serializers
from .models import *
from django.contrib.auth import get_user_model

User = get_user_model()


class UserCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'username', 'password', 'karma_points', 'device_id'
        ]

    def create(self, validated_data):
        print(validated_data)
        username = validated_data['username']
        # email = validated_data['email']
        # password = validated_data['password']
        karma_points = validated_data['karma_points']
        device_id = validated_data['device_id']
        user_obj = User(
            username = username,
            email = 'testing@google.com',
            karma_points = karma_points,
            device_id = device_id,
            is_staff = True
        )
        def_pwd = 'admin'
        user_obj.set_password(def_pwd)
        user_obj.save()
        return validated_data


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
                  'cover_id', 'source_url', 'rating', 'created_by']


class EventCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = ['name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url', 'rating', 'created_by']


class FoodDealSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodDeal
        fields = ['id', 'post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating', 'title', 'start_date', 'end_date', 'start_time', 'end_time', 'created_by', 'location']



class FoodDealCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodDeal
        fields = ['post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating', 'title', 'start_date', 'end_date', 'start_time', 'end_time', 'created_by','location']


# Food Place
class FoodPlaceSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['id', 'name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link', 'rating', 'created_by']


class FoodPlaceCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link', 'rating', 'created_by']
