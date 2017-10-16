from django.db import models
import datetime

class MobileUser(models.Model):
    username = models.CharField(max_length=200)
    device_id = models.CharField(max_length=200)
    karma_points = models.CharField(max_length=100, default='0')


class Event(models.Model):
    # data
    facebook_id = models.CharField(max_length=50, blank=True)
    name = models.CharField(max_length=500)
    description = models.TextField(blank=True, default='')
    # date time
    start_date = models.DateField()
    end_date = models.DateField(blank=True, default='')
    start_time = models.TimeField()
    end_time = models.TimeField(blank=True, default='')
    # location
    place_name = models.CharField(max_length=200, blank=True, default='')
    city = models.CharField(max_length=100, blank=True, default='')
    country = models.CharField(max_length=100, blank=True, default='')
    latitude = models.CharField(max_length=100, blank=True, default='')
    longitude = models.CharField(max_length=100, blank=True, default='')
    state = models.CharField(max_length=100, blank=True, default='')
    street = models.CharField(max_length=200, blank=True, default='')
    zip = models.CharField(max_length=5, blank=True, default='')
    # cover photo
    cover_id = models.CharField(max_length=50, blank=True, default='')
    source_url = models.CharField(max_length=200, blank=True, default='')
    rating = models.CharField(max_length=100, default='0')
    created_by = models.CharField(max_length=100)


class FoodDeal(models.Model):
    post_id = models.CharField(max_length=100, blank=True)
    message = models.TextField()
    updated_date = models.DateField(blank=True, null=True)
    updated_time = models.TimeField(blank=True, null=True)
    photo_link = models.CharField(max_length=400, blank=True, default='')
    event_link = models.CharField(max_length=400, blank=True, default='')
    rating = models.CharField(max_length=100, default='0')
    title = models.CharField(max_length=100, blank=True)
    start_date = models.DateField(blank=True, null=True)
    end_date = models.DateField(blank=True, null=True)
    start_time = models.TimeField(blank=True, null=True)
    end_time = models.TimeField(blank=True, null=True)
    created_by = models.CharField(max_length=100)
    location = models.CharField(max_length=200, blank=True, default='')


class FoodPlace(models.Model):
    name = models.CharField(max_length=500)
    location = models.CharField(max_length=200)
    price_level = models.CharField(max_length=2, blank=True, default='')
    google_rating = models.CharField(max_length=3, blank=True, default='')
    latitude = models.CharField(max_length=100, blank=True)
    longitude = models.CharField(max_length=100, blank=True)
    photo_link = models.CharField(max_length=500, blank=True, default='')
    rating = models.CharField(max_length=100, default='0')
    created_by = models.CharField(max_length=100)
