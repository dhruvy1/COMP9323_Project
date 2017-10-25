from django.contrib.auth import authenticate, login
from django.shortcuts import redirect
from django.views.generic import View
from rest_framework import status
from rest_framework.decorators import permission_classes
from rest_framework.generics import ListAPIView, RetrieveAPIView, UpdateAPIView, DestroyAPIView, CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST
from rest_framework.views import APIView

from .serializers import *


# Events views
@permission_classes((AllowAny,))
class EventListAPIView(ListAPIView):
    # List all events
    queryset = Event.objects.all()
    serializer_class = EventSerializer


@permission_classes((AllowAny,))
class EventCreateAPIView(CreateAPIView):
    queryset = Event.objects.all()
    serializer_class = EventCreateUpdateSerializer

    # Custom POST method to check for already existing Event
    def post(self, request, *args, **kwargs):

        # Retrieve all Food deals from the DB
        events = Event.objects.all()

        for ev in events:
            # If event in the request already exists in the DB
            if 'facebook_id' in request.data and request.data['facebook_id'] == ev.facebook_id:

                # Return appropriate HTTP response
                print(ev.facebook_id + ' Already exists')
                content = {'Already exists': str(ev.facebook_id)}
                return Response(content, status=status.HTTP_202_ACCEPTED)

        # If event does not exist, add it to the DB
        return self.create(request, *args, **kwargs)


@permission_classes((AllowAny,))
class EventUpdateAPIView(UpdateAPIView):
    queryset = Event.objects.all()
    serializer_class = EventCreateUpdateSerializer


@permission_classes((AllowAny,))
class EventDetailAPIView(RetrieveAPIView):
    queryset = Event.objects.all()
    serializer_class = EventSerializer


@permission_classes((AllowAny,))
class EventDestroyAPIView(DestroyAPIView):
    queryset = Event.objects.all()
    serializer_class = EventSerializer


# Food Deals
@permission_classes((AllowAny,))
class FoodDealListAPIView(ListAPIView):
    queryset = FoodDeal.objects.all()
    serializer_class = FoodDealSerializer


@permission_classes((AllowAny,))
class FoodDealCreateAPIView(CreateAPIView):
    queryset = FoodDeal.objects.all()
    serializer_class = FoodDealCreateUpdateSerializer

    # Custom POST method to check for already existing Food Deal
    def post(self, request, *args, **kwargs):

        # Retrieve all Food deals from the DB
        food_deals = FoodDeal.objects.all()

        for fd in food_deals:
            # If post_id in the request already exists in the DB
            if 'post_id' in request.data and request.data['post_id'] == fd.post_id:

                # Return appropriate HTTP response
                print(fd.post_id + ' Already exists')
                content = {'Already exists': str(fd.post_id)}
                print(len(food_deals))
                return Response(content, status=status.HTTP_202_ACCEPTED)

        # If post_id does not exist, add it to the DB
        return self.create(request, *args, **kwargs)


@permission_classes((AllowAny,))
class FoodDealUpdateAPIView(UpdateAPIView):
    queryset = FoodDeal.objects.all()
    serializer_class = FoodDealCreateUpdateSerializer


@permission_classes((AllowAny,))
class FoodDealDetailAPIView(RetrieveAPIView):
    queryset = FoodDeal.objects.all()
    serializer_class = FoodDealSerializer


@permission_classes((AllowAny,))
class FoodDealDestroyAPIView(DestroyAPIView):
    queryset = FoodDeal.objects.all()
    serializer_class = FoodDealSerializer


# Food Place Views
@permission_classes((AllowAny,))
class FoodPlaceListAPIView(ListAPIView):
    queryset = FoodPlace.objects.all()
    serializer_class = FoodPlaceSerializer


@permission_classes((AllowAny,))
class FoodPlaceCreateAPIView(CreateAPIView):
    queryset = FoodPlace.objects.all()
    serializer_class = FoodPlaceCreateUpdateSerializer

    # Custom POST method to check for already existing Food Place
    def post(self, request, *args, **kwargs):
        # Retrieve all Food deals from the DB
        foodplaces = FoodPlace.objects.all()

        for fp in foodplaces:
            # If Place name in the request already exists in the DB
            if request.data['name'] == fp.name:
                # Return appropriate HTTP response
                print(fp.name + ' Already exists')
                content = {'Already exists': str(fp.name)}
                print(len(foodplaces))
                return Response(content, status=status.HTTP_202_ACCEPTED)

        # If name does not exist, add it to the DB
        return self.create(request, *args, **kwargs)


@permission_classes((AllowAny,))
class FoodPlaceUpdateAPIView(UpdateAPIView):
    queryset = FoodPlace.objects.all()
    serializer_class = FoodPlaceCreateUpdateSerializer


@permission_classes((AllowAny,))
class FoodPlaceDetailAPIView(RetrieveAPIView):
    queryset = FoodPlace.objects.all()
    serializer_class = FoodPlaceSerializer


@permission_classes((AllowAny,))
class FoodPlaceDestroyAPIView(DestroyAPIView):
    queryset = FoodPlace.objects.all()
    serializer_class = FoodPlaceSerializer


# User views
@permission_classes((AllowAny,))
class UserAPIView(CreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserCreateSerializer


@permission_classes((AllowAny,))
class UsersAPIView(ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserDetailSerializer


@permission_classes((AllowAny,))
class UserListAPIView(ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserDetailSerializer


@permission_classes((AllowAny,))
class UserDetailAPIView(RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserDetailSerializer


@permission_classes((AllowAny,))
class UserUpdateAPIView(UpdateAPIView):
    queryset = User.objects.all()
    serializer_class = UserUpdateSerializer


class UserLoginAPIView(APIView):
    # permission_classes = [IsAu]
    queryset = User.objects.all()
    serializer_class = UserLoginSerializer

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            new_data = serializer.data
            return Response(new_data, status=HTTP_200_OK)
        return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)