"""appserver URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add an import:  from blog import urls as blog_urls
    2. Add a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
"""
from django.conf.urls import include, url
from django.conf import settings
from django.contrib import admin
from .views import *

urlpatterns = [
    url(r'^api/', include('app_api.urls', namespace='app_api')),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^qa/', include('qa.urls')),
    url(r'^qalogin/', index.as_view(), name='index'),
    url(r'^voteadd/', inc.as_view(), name='increment'),
    url(r'^votesub/', dec.as_view(), name='decrement'),

]
