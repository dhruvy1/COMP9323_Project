# COMP9323_Project

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

## Run the server without active shell
```bash
# From ~/
sudo nohup COMP9323_Project/venv/bin/python3 COMP9323_Project/server/appserver/manage.py runserver 0:8000
```