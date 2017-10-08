from django.http import HttpResponse
from rest_framework import status
from rest_framework.decorators import permission_classes
from rest_framework.generics import ListAPIView, RetrieveAPIView, UpdateAPIView, DestroyAPIView, CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response

from .serializers import *


def index(request):
    return HttpResponse("Hello, world.")


@permission_classes((AllowAny,))
class MobileUserAPIView(ListAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserCreateSerializer


@permission_classes((AllowAny,))
class MobileUserCreateAPIView(CreateAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserSerializer


@permission_classes((AllowAny,))
class MobileUserDetailAPIView(RetrieveAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserSerializer


@permission_classes((AllowAny,))
class MobileUserUpdateAPIView(UpdateAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserSerializer


@permission_classes((AllowAny,))
class MobileUserDestroyAPIView(DestroyAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserSerializer


# Events
@permission_classes((AllowAny,))
class EventListAPIView(ListAPIView):
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
            if request.data['facebook_id'] == ev.facebook_id:

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
    queryset = MobileUser.objects.all()
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
            if request.data['post_id'] == fd.post_id:

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


# Food Place
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


