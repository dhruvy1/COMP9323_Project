from django.conf.urls import url
from .views import *
from rest_framework_swagger.views import get_swagger_view
schema_view = get_swagger_view(title='9323 API')

urlpatterns = [
    # Swagger URL
    url(r'^$', schema_view),
    # Index URL
    url(r'^$', index, name='index'),
    # mobile_user list URL
    url(r'mobile_user/$', MobileUserAPIView.as_view(), name='list'),
    # mobile_user_detail URLs
    url(r'mobile_user_detail/create/$', MobileUserCreateAPIView.as_view(), name='create'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/$', MobileUserDetailAPIView.as_view(), name='detail'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/edit/$', MobileUserUpdateAPIView.as_view(), name='update'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/delete/$', MobileUserDestroyAPIView.as_view(), name='destroy'),
    # Event urls
    url(r'event/all/$', EventListAPIView.as_view(), name='list'),
    url(r'event/(?P<pk>[0-9]+)/$', EventDetailAPIView.as_view(), name='detail'),
    url(r'event/create/$', EventCreateAPIView.as_view(), name='create'),
    url(r'event/(?P<pk>[0-9]+)/$', EventUpdateAPIView.as_view(), name='update'),
    url(r'event/(?P<pk>[0-9]+)/$', EventDestroyAPIView.as_view(), name='destroy'),
]
