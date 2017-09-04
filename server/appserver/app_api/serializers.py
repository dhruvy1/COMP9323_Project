from rest_framework import serializers
from .models import *


class MobileUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = MobileUser
        fields = ['username', 'device_id',]