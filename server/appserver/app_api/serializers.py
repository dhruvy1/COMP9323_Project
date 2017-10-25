from rest_framework import serializers
from .models import *
from django.contrib.auth import get_user_model
from django.db.models import Q
from rest_framework.serializers import ValidationError

User = get_user_model()


class UserDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'id', 'username', 'karma_points', 'device_id'
        ]
        extra_kwargs = {"username": {"read_only": True}}


class UserUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'username', 'karma_points', 'device_id'
        ]
        extra_kwargs = {"username": {"read_only": True}}


class UserCreateSerializer(serializers.ModelSerializer):
    id = serializers.ReadOnlyField()

    class Meta:
        model = User
        fields = [
            'id', 'username', 'karma_points', 'device_id'
        ]

    def create(self, validated_data):
        print(validated_data)
        username = validated_data['username']
        # password = validated_data['password']
        karma_points = validated_data['karma_points']
        device_id = validated_data['device_id']
        user_obj = User(
            username=username,
            karma_points=karma_points,
            device_id=device_id,
            is_staff=True
        )
        def_pwd = device_id
        user_obj.set_password(def_pwd)
        user_obj.save()
        return user_obj


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
        fields = ['id', 'post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating',
                  'title', 'start_date', 'end_date', 'start_time', 'end_time', 'created_by', 'location']


class FoodDealCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodDeal
        fields = ['post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating', 'title',
                  'start_date', 'end_date', 'start_time', 'end_time', 'created_by', 'location']


# Food Place
class FoodPlaceSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['id', 'name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link',
                  'rating', 'created_by']


class FoodPlaceCreateUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoodPlace
        fields = ['name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude', 'photo_link', 'rating',
                  'created_by']


class UserLoginSerializer(serializers.ModelSerializer):
    username = serializers.CharField(required=True)
    token = serializers.CharField(allow_blank=True, read_only=True)

    class Meta:
        model = User
        fields = [
            'username', 'password', 'token'
        ]
        extra_kwargs = {"password": {"write_only": True}}

    def validate(self, data):
        user_obj = None
        password = data["password"]
        username = data.get("username")
        user = User.objects.filter(
            Q(username=username)
        )
        if user.exists() and user.count() == 1:
            user_obj = user.first()
        else:
            raise ValidationError("This username is not valid")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("Incorrect password")
        data["token"] = "Test_token"
        return data
