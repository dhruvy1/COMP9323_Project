from django.conf.urls import url
from .views import *

urlpatterns = [
    url(r'^$', index, name='index'),
    url(r'mobile_user/$', MobileUserAPIView.as_view(), name='list'),
    url(r'mobile_user_detail/create/$', MobileUserCreateAPIView.as_view(), name='create'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/$', MobileUserDetailAPIView.as_view(), name='detail'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/edit/$', MobileUserUpdateAPIView.as_view(), name='update'),
    url(r'mobile_user_detail/(?P<pk>[0-9]+)/delete/$', MobileUserDestroyAPIView.as_view(), name='delete'),

]
