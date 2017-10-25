from django.conf.urls import url, include
from .views import *
from rest_framework_swagger.views import get_swagger_view
schema_view = get_swagger_view(title='Campus Mate')

urlpatterns = [
    # Swagger URL
    url(r'^$', schema_view),
    # mobile_user list URL
    # url(r'mobile_users/all/$', MobileUserAPIView.as_view(), name='list'),
    # User urls
    url(r'users/$', UserAPIView.as_view(), name='create'),
    url(r'users/all/$', UserListAPIView.as_view(), name='list'),
    url(r'users/(?P<pk>[0-9]+)$', UserUpdateAPIView.as_view(), name='update'),
    url(r'users/(?P<pk>[0-9]+)/$', UserDetailAPIView.as_view(), name='detail'),

    # url(r'login/$', UserLoginAPIView.as_view(), name='login'),
    # mobile_user_detail URLs
    # url(r'mobile_users/$', MobileUserCreateAPIView.as_view(), name='create'),
    # url(r'mobile_users/(?P<pk>[0-9]+)/$', MobileUserDetailAPIView.as_view(), name='detail'),
    # url(r'mobile_users/(?P<pk>[0-9]+)$', MobileUserUpdateAPIView.as_view(), name='update'),
    # url(r'mobile_users/(?P<pk>[0-9]+)$', MobileUserDestroyAPIView.as_view(), name='destroy'),
    # Event urls
    url(r'events/all/$', EventListAPIView.as_view(), name='list'),
    url(r'events/$', EventCreateAPIView.as_view(), name='create'),
    url(r'events/(?P<pk>[0-9]+)/$', EventDetailAPIView.as_view(), name='detail'),
    url(r'events/(?P<pk>[0-9]+)$', EventUpdateAPIView.as_view(), name='update'),
    url(r'events/(?P<pk>[0-9]+)$', EventDestroyAPIView.as_view(), name='destroy'),
    # Food Deals
    url(r'food_deals/all/$', FoodDealListAPIView.as_view(), name='list'),
    url(r'food_deals/$', FoodDealCreateAPIView.as_view(), name='create'),
    url(r'food_deals/(?P<pk>[0-9]+)/$', FoodDealDetailAPIView.as_view(), name='detail'),
    url(r'food_deals/(?P<pk>[0-9]+)$', FoodDealUpdateAPIView.as_view(), name='update'),
    url(r'food_deals/(?P<pk>[0-9]+)$', FoodDealDestroyAPIView.as_view(), name='destroy'),
    # Food Place
    url(r'food_places/all/$', FoodPlaceListAPIView.as_view(), name='list'),
    url(r'food_places/$', FoodPlaceCreateAPIView.as_view(), name='create'),
    url(r'food_places/(?P<pk>[0-9]+)/$', FoodPlaceDetailAPIView.as_view(), name='detail'),
    url(r'food_places/(?P<pk>[0-9]+)$', FoodPlaceUpdateAPIView.as_view(), name='update'),
    url(r'food_places/(?P<pk>[0-9]+)$', FoodPlaceDestroyAPIView.as_view(), name='destroy'),
]
