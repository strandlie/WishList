import mysql.connector as msc
import flask_jsonpify

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from sqlalchemy import Table, Text, Column, Integer, String, MetaData, ForeignKey, ForeignKeyConstraint, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, sessionmaker

Base = declarative_base()
app = Flask(__name__)
api = Api(app)


"""
Association tables
"""
personInGroup = Table('person_in_group', Base.metadata,
    Column('person_id', ForeignKey(Person.person_id), primary_key=True),
    Column('group_id', ForeignKey(Group.group_id),  primary_key=True)
)

"""
ORM classes
"""
class Person(Base):
    __tablename__ = 'person'

    person_id = Column(Integer, primary_key=True)
    name = Column(String(30))
    
    groups = relationship(Group, secondary=personInGroup, back_populates=Group.members)
    wishes = relationship
    purchases = 

    def __init__(self, name):
        self.name = name

    def addGroup(self, group):
        if not group in self.groups:
            self.groups.append(group)

    def isInGroup(self, group):
        return group in self.groups


class Group(Base):
    __tablename__ = 'group'
    
    group_id = Column(Integer, primary_key=True)
    name = Column(String(50), nullable=False)

    members = relationship(Person, secondary=personInGroup, back_populates=Person.groups)

    def __init__(self, name):
        self.name = name

    def addMember(self, person):
        if not person in self.members:
            self.members.append(person)
    
    def isMember(self, person):
        return person in self.members


class Gift(Base):
    __tablename__ = 'gift'

    gift_id = Column(Integer, primary_key=True)
    title = Column(String(100), nullable=False)
    description = Column(String(200)),
    url = Column(String(200))

    wishes = 

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
    


