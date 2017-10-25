from app_api.models import User
from django.contrib.auth import authenticate, login
from django.shortcuts import redirect
from django.views.generic import View
from django.http import HttpResponse

class index(View):

    def get(self, request):

        # Get device ID from URL
        dev_id = request.GET["device"]
        print(str(dev_id))

        all_users = User.objects.filter(device_id=dev_id)
        # Get associated username
        un = all_users[0].get_un()
        print('User: ' + str(un))

        # Auth
        user = authenticate(username=un, password=dev_id)

        # Login
        login(request, user)
        return redirect('http://127.0.0.1:8000/qa/')
        # return redirect('http://52.65.129.3:8000/qa/')


class inc(View):

    def get(self, request):
        dev_id = request.GET["device"]
        print(str(dev_id))
        # Get device ID from URL
        dev_id = request.GET["device"]
        print(str(dev_id))

        all_users = User.objects.filter(device_id=dev_id)
        # Call increment
        if len(all_users) > 0:
            all_users[0].inc_app_points()
            return HttpResponse(status=200)
        else:
            print('no user with dev_id')
            return HttpResponse(status=400)


class dec(View):

    def get(self, request):
        dev_id = request.GET["device"]
        print(str(dev_id))
        # Get device ID from URL
        dev_id = request.GET["device"]
        print(str(dev_id))

        all_users = User.objects.filter(device_id=dev_id)
        # Call increment
        if len(all_users) > 0:
            all_users[0].dec_app_points()
            return HttpResponse(status=200)
        else:
            print('no user with dev_id')
            return HttpResponse(status=400)
