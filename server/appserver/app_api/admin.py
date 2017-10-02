from django.contrib import admin
from .models import *


class MobileUserAdmin(admin.ModelAdmin):
    list_display = ('username', 'device_id')
admin.site.register(MobileUser, MobileUserAdmin)


class FoodPlaceAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'location', 'price_level', 'google_rating', 'latitude', 'longitude')
admin.site.register(FoodPlace, FoodPlaceAdmin)
