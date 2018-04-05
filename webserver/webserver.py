import mysql.connector as msc
import flask_jsonpify

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, ForeignKeyConstraint, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, sessionmaker

Base = declarative_base()
app = Flask(__name__)
api = Api(app)

"""
ORM classes
"""
class Person(Base):
    __tablename__ = 'person'
    
    person_id = Column(Integer, primary_key=True)
    name = Column(String(30))


class Group(Base):
    __tablename__ = 'group'
    
    group_id = Column(Integer, primary_key=True)
    name = Column(String(50), nullable=False)


class PersonInGroup(Base):
    __tablename__ = 'person_in_group'

    person_id = Column(Integer, ForeignKey(Person.person_id), primary_key=True)
    group_id = Column(Integer, ForeignKey(Group.group_id),  primary_key=True)

    person = relationship(Person)
    group = relationship(Group)

class Gift(Base):
    __tablename__ = 'gift'

    gift_id = Column(Integer, primary_key=True)
    title = Column(String(100), nullable=False)
    description = Column(String(200)),
    url = Column(String(200))

class Wish(Base):
    __tablename__ = 'wish'

    recepient_id = Column(Integer, ForeignKey(Person.person_id), primary_key=True)
    group_id = Column(Integer, ForeignKey(Group.group_id), primary_key=True)
    gift_id = Column(Integer, ForeignKey(Gift.gift_id), primary_key=True)
    quantity = Column(Integer, nullable=False)

    recepient = relationship(Person)
    group = relationship(Group)
    gift = relationship(Gift)


class WishFulfilled(Base):
    __tablename__ = 'wish_fulfilled'

    buyer_id = Column(Integer, ForeignKey(Person.person_id), primary_key=True)
    recepient_id = Column(Integer, primary_key=True)
    group_id = Column(Integer, primary_key=True)
    gift_id = Column(Integer, primary_key=True)
    quantity = Column(Integer, nullable=False)

    # Relationship for composite foreign key
    __table_args__  = (ForeignKeyConstraint([recepient_id, group_id, gift_id], [Wish.recepient_id, Wish.group_id, Wish.gift_id]), )
    


"""
Create the tables
"""
def create_tables():

    engine = create_engine('mysql+mysqlconnector://tutorial_user:tutorial_pw@tutorial-db-instance.c0lilbtiidoe.eu-central-1.rds.amazonaws.com/sample')

    # Bind the engine to the metadata of the Base class so that the
    # declaratives can be accessed through a DBSession instance
    Base.metadata.create_all(engine)
     
    DBSession = sessionmaker(bind=engine)
    # A DBSession() instance establishes all conversations with the database
    # and represents a "staging zone" for all the objects loaded into the
    # database session object. Any change made against the objects in the
    # session won't be persisted into the database until you call
    # session.commit(). If you're not happy about the changes, you can
    # revert all of them back to the last commit by calling
    # session.rollback()
    session = DBSession()

if __name__ == "__main__":
    create_tables()

