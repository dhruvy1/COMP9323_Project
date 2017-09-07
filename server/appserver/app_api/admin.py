from django.contrib import admin
from .models import *


class MobileUserAdmin(admin.ModelAdmin):
    list_display = ('username', 'device_id')
admin.site.register(MobileUser, MobileUserAdmin)