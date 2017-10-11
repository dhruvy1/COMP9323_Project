#! /bin/bash

source venv/bin/activate
python3 server/appserver/manage.py makemigrations app_api
python3 server/appserver/manage.py migrate
python3 server/appserver/manage.py runserver
deactivate
