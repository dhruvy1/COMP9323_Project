from django.conf.urls import url
from .views import *
from rest_framework_swagger.views import get_swagger_view
schema_view = get_swagger_view(title='Campus Mate')

urlpatterns = [
    # Swagger URL
    url(r'^$', schema_view),
    # Index URL
    url(r'^$', index, name='index'),
    # mobile_user list URL
    url(r'mobile_users/all/$', MobileUserAPIView.as_view(), name='list'),
    # mobile_user_detail URLs
    url(r'mobile_users/$', MobileUserCreateAPIView.as_view(), name='create'),
    url(r'mobile_users/(?P<pk>[0-9]+)/$', MobileUserDetailAPIView.as_view(), name='detail'),
    url(r'mobile_users/(?P<pk>[0-9]+)/$', MobileUserUpdateAPIView.as_view(), name='update'),
    url(r'mobile_users/(?P<pk>[0-9]+)/$', MobileUserDestroyAPIView.as_view(), name='destroy'),
    # Event urls
    url(r'events/all/$', EventListAPIView.as_view(), name='list'),
    url(r'events/$', EventCreateAPIView.as_view(), name='create'),
    url(r'events/(?P<pk>[0-9]+)/$', EventDetailAPIView.as_view(), name='detail'),
    url(r'events/(?P<pk>[0-9]+)/$', EventUpdateAPIView.as_view(), name='update'),
    url(r'events/(?P<pk>[0-9]+)/$', EventDestroyAPIView.as_view(), name='destroy'),
    # Food Deals
    url(r'food_deals/all/$', FoodDealListAPIView.as_view(), name='list'),
    url(r'food_deals/$', FoodDealCreateAPIView.as_view(), name='create'),
    url(r'food_deals/(?P<pk>[0-9]+)/$', FoodDealDetailAPIView.as_view(), name='detail'),
    url(r'food_deals/(?P<pk>[0-9]+)/$', FoodDealUpdateAPIView.as_view(), name='update'),
    url(r'food_deals/(?P<pk>[0-9]+)/$', FoodDealDestroyAPIView.as_view(), name='destroy'),
]
