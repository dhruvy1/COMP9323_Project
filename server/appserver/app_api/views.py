from django.http import HttpResponse
from rest_framework.generics import ListAPIView

from .serializers import *


def index(request):
    return HttpResponse("Hello, world.")


class MobileUserAPIView(ListAPIView):
    queryset = MobileUser.objects.all()
    serializer_class = MobileUserSerializer
