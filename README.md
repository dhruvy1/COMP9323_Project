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

## Starting the server
```bash
cd appserver
python manage.py runserver
```