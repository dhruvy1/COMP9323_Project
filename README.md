# COMP9323_Project

#### The API of this project can be viewed at (AWS Hosted): http://52.65.129.3:8000/api/
Feel free to use Swagger to test the API. e.g. http://52.65.129.3:8000/api/#!/food_deals/food_deals_all_list 

Key code files for the server backend are:
* (Project URLs) server/appserver/app_api/urls.py
* (Database model) server/appserver/app_api/models.py
* (API views) server/appserver/app_api/views.py
* (JSON parsers) server/appserver/app_api/serializers.py
* (Q&A files) venv/lib/python3.4/site-packages/qa/

Main files for the application:
* (Event Page) AndroidApplication/app/src/main/java/com/comp9323/event/*
* (Food Places/Deals) AndroidApplication/app/src/main/java/com/comp9323/food/*
* (Q&A) AndroidApplication/app/src/main/java/com/comp9323/qa/*
* (Rest Client Calls) AndroidApplication/app/src/main/java/com/comp9323/restclient/*

#### Instructions on how to use the Android app are avaliable in `Getting_Started_Guide_on_Campus_Mate_app.pdf`
---
Instructions to deploy the server locally are specfied below (This will create a local version of the app with no data in the DB):

## Creating a Python virtualenv
```bash
pip install virtualenv
python3 -m venv venv
source venv/bin/activate
cd server
pip install --upgrade pip
pip install -r requirements.txt
```

## Construct the DB
```bash
cd appserver
python manage.py makemigrations app_api
python manage.py migrate
```

## Starting the server
```bash
cd appserver
python manage.py runserver
```

## Run the server without active shell (for AWS)
```bash
# From ~/
sudo nohup COMP9323_Project/venv/bin/python3 COMP9323_Project/server/appserver/manage.py runserver 0:8000
```

## Set up instruction for android project
Please read this file "Getting_Started_Guide_on_Campus_Mate_app.pdf"
