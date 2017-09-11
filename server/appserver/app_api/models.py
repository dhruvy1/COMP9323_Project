from django.db import models


class MobileUser(models.Model):
    username = models.CharField(max_length=200)
    device_id = models.CharField(max_length=200)


class Event(models.Model):
    # data
    facebook_id = models.CharField(max_length=50)
    name = models.CharField(max_length=500)
    description = models.TextField(blank=True, default='')
    # date time
    start_date = models.DateField()
    end_date = models.DateField()
    start_time = models.TimeField()
    end_time = models.TimeField()
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


class FoodDeal(models.Model):
    post_id = models.CharField(max_length=100)
    message = models.TextField()
    updated_time = models.TimeField()
    photo_link = models.CharField(max_length=400, blank=True, default='')
    event_link = models.CharField(max_length=400, blank=True, default='')
