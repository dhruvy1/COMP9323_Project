from django.db import models


class MobileUser(models.Model):
    username = models.CharField(max_length=200)
    device_id = models.CharField(max_length=200)


class Event(models.Model):
    name = models.CharField(max_length=500)
    description = models.TextField()
    start_time = models.TimeField()
    facebook_id = models.CharField(max_length=50)
    end_time = models.TimeField()
    place = models.ForeignKey('Place')


class Place(models.Model):
    name = models.CharField(max_length=200)
    location = models.ForeignKey('Location')


class Location(models.Model):
    city = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    latitude = models.CharField(max_length=100)
    longitude = models.CharField(max_length=100)
    state = models.CharField(max_length=100)
    street = models.CharField(max_length=200)
    zip = models.CharField(max_length=5)
