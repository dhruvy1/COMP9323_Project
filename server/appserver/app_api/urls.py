from django.conf.urls import url
from .views import *

urlpatterns = [
    url(r'^$', index, name='index'),
    url(r'mobile_user/$', MobileUserAPIView.as_view(), name='list'),
]
