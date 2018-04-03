import mysql.connector as msc

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from flask.ext.jsonpify import jsonify
from msc import MySQLCursorPrepared
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey

"""
Connect to the db
"""
def connect_to_db():
    try:
        cnx = msc.connect(user='tutorial_user', password='tutorial_pw',
              host='tutorial-db-instance.c0lilbtiidoe.eu-central-1.rds.amazonaws.com',
              database='sample')
        return cnx

    except mysql.connector.Error as err:
        print(err)

app = Flask(__name__)
api = API(app)

"""
Create the tables
"""
def create_tables():
    metadata = MetaData()
    person = Table('person', metadata,
            Column('person_id', Integer, primary_key=True),
            Column('name', String)
            )

    group = Table('group', metadata,
            Column('group_id', Integer, primary_key=True),
            Column('group_name', String)
            )

    person_in_group = Table('person_in_group', metadata,
            Column('person_id', None, primary_key=True, ForeignKey('person.person_id')),
            Column('group_id', None, primary_key=True,  ForeignKey('group.group_id'))
            )

    gift = Table('gift', metadata,
            Column('gift_id', Integer, primary_key=True),
            Column('title', String),
            Column('description', String),
            Column('url', String)
            )

    wish = Table('wish', metadata,
            Column('person_id', None, primary_key=True, ForeignKey('person.person_id'),
            Column('group_id', None, primary_key=True, ForeignKey('group.group_id'),
            Column('gift_id', None, primary



    


class Person(Resource):

    """
    Get all persons from database
    """
    def get(self):
        db_connect = connect_to_db()
        conn = db_connect.connect()
        query = conn.execute("SELECT * FROM person")

    def get(self, person_id):
        db_connect = connect_to_db()
        conn = db_connect.connect()
        cursor = conn.cursor(cursor_class=MSQLCursorPrepared, prepared=True)
        statement = "SELECT * FROM person WHERE person_id = %s"
        cursor.execute(statement, (person_id))


