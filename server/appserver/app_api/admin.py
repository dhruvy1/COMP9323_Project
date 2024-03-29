from django.contrib import admin
from .models import *

# Register Models to view in the admin section.


class UserAdmin(admin.ModelAdmin):
    list_display_links = ('id','username')
    list_display = ('id','username', 'device_id', 'karma_points')
admin.site.register(User, UserAdmin)


class FoodPlaceAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude')
admin.site.register(FoodPlace, FoodPlaceAdmin)


class FoodDealAdmin(admin.ModelAdmin):
    list_display = ('id', 'post_id', 'message', 'updated_time', 'photo_link', 'event_link', 'updated_date', 'rating', 'created_by', 'location')
admin.site.register(FoodDeal, FoodDealAdmin)


class EventAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'description', 'start_time', 'facebook_id', 'start_date', 'end_date', 'end_time',
                  'place_name', 'city', 'country', 'latitude', 'longitude', 'state', 'street', 'zip',
                  'cover_id', 'source_url', 'rating', 'created_by')
admin.site.register(Event, EventAdmin)