from django.db import models


class MobileUser(models.Model):
    username = models.CharField(max_length=200)
    device_id = models.CharField(max_length=200)


class Event(models.Model):
    # data
    facebook_id = models.CharField(max_length=50)
    name = models.CharField(max_length=500)
    description = models.TextField()
    # date time
    start_date = models.DateField()
    end_date = models.DateField()
    start_time = models.TimeField()
    end_time = models.TimeField()
    # location
    place_name = models.CharField(max_length=200)
    city = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    latitude = models.CharField(max_length=100)
    longitude = models.CharField(max_length=100)
    state = models.CharField(max_length=100)
    street = models.CharField(max_length=200)
    zip = models.CharField(max_length=5)
    # cover photo
    cover_id = models.CharField(max_length=50)
    source_url = models.CharField(max_length=200)

    def __str__(self):
        my_string = "facebook_id: " + self.facebook_id + "\n"
        my_string += "name: " + self.name + "\n"
        my_string += "description: " + self.description + "\n"
        my_string += "start_date: " + str(self.start_date) + "\n"
        my_string += "end_date: " + str(self.end_date) + "\n"
        my_string += "start_time: " + str(self.start_time) + "\n"
        my_string += "end_time: " + str(self.end_time) + "\n"
        my_string += "place_name: " + self.place_name + "\n"
        my_string += "city: " + self.city + "\n"
        my_string += "country: " + self.country + "\n"
        my_string += "latitude: " + str(self.latitude) + "\n"
        my_string += "longitude: " + str(self.longitude) + "\n"
        my_string += "state: " + self.state + "\n"
        my_string += "street: " + self.street + "\n"
        my_string += "zip: " + str(self.zip) + "\n"
        my_string += "cover_id: " + self.cover_id + "\n"
        my_string += "source_url: " + self.source_url + "\n"
        return my_string
