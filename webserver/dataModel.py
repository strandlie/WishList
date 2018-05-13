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
personInGroup = Table('person_in_gift_group', Base.metadata,
    Column('person_id', ForeignKey('person.person_id'), primary_key=True),
    Column('gift_group_id', ForeignKey('gift_group.gift_group_id'),  primary_key=True)
)

"""
ORM classes
"""
class Person(Base):
    __tablename__ = 'person'

    person_id = Column(Integer, primary_key=True)
    name = Column(String(30))
    
    gift_groups = relationship('Group', secondary=personInGroup, back_populates='members')
    wishes = relationship('Wish', back_populates='recepient')
    fulfilments = relationship('WishFulfilled', back_populates='fulfiller')

    def __init__(self, name):
        self.name = name

    def addToGroup(self, gift_group):
        if not isInGroup(gift_group):
            self.gift_groups.append(gift_group)

    def removeFromGroup(self, gift_group):
        if isInGroup(gift_group):
            self.gift_groups.remove(gift_group)

    def isInGroup(self, gift_group):
        return gift_group in self.gift_groups


class Group(Base):
    __tablename__ = 'gift_group'
    
    gift_group_id = Column(Integer, primary_key=True)
    name = Column(String(50), nullable=False)

    # Since Person-Group is a many-to-many relationship, we use the secondary 
    # personInGroup Table to map this relationship
    members = relationship('Person', secondary=personInGroup, back_populates='gift_groups')
    wishes = relationship('Wish', back_populates='gift_group')

    def __init__(self, name):
        self.name = name

    def addMember(self, person):
        if not person in self.members:
            self.members.append(person)
    
    def isMember(self, person):
        return person in self.members

    def getWishes(self):
        return self.wishes

    
    def hasWish(self, wish):
        return wish in self.wishes


class Gift(Base):
    __tablename__ = 'gift'

    gift_id = Column(Integer, primary_key=True)
    title = Column(String(100), nullable=False)
    description = Column(String(200)),
    url = Column(String(200))

    # Since no ForeignKeys are defined in this class
    # the relationship will be defined by the ForeignKey in Wish, 
    # which means that this is a list of all wishes that this 
    # gift is a part of. 
    wishes = relationship('Wish', back_populates='gift')
    fulfilments = relationship('WishFulfilled', back_populates='gift')

    def __init__(self, title = None, description = None, url = None):
        self.title = title
        self.description = description
        self.url = url

    def setTitle(self, title):
        if len(title):
            self.title = title
    
    def getTitle(self):
        return self.title

    def setDescription(self, description):
        if len(description):
            self.description = description
        else:
            self.removeDescription()

    def removeDescription(self):
        self.description = None

    def getDescription(self):
        return self.description


    def setUrl(self, url):
        if len(url):
            self.url = url
        else:
            self.removeUrl()

    def removeUrl(self):
        self.url = None

    def getUrl(self):
        return self.url

    def getWishes(self):
        return self.wishes

    def isPartOfWish(self, wish):
        return wish in self.wishes

    def getNumberOfFulfilments(self):
        return len(fulfilments)



class Wish(Base):
    __tablename__ = 'wish'

    # Has an ID-field for every part of the primary key
    # These are set automatically from the objects in the relationships below.
    # Does not need to be set explicitly
    recepient_id = Column(Integer, ForeignKey('person.person_id'), primary_key=True)
    gift_group_id = Column(Integer, ForeignKey('gift_group.gift_group_id'), primary_key=True)
    gift_id = Column(Integer, ForeignKey('gift.gift_id'), primary_key=True)
    quantity = Column(Integer, nullable=False)

    # The relationships are configured from the ForeignKeys above
    recepient = relationship('Person', back_populates='wishes')
    gift_group = relationship('Group', back_populates='wishes')
    gift = relationship('Gift', back_populates='wishes')


    def __init__(self, recepient, gift_group, gift, quantity):
        if recepient == None or gift_group == None or gift == None or quantity == None:
            raise ValueError("No argument to Wish can be None.")
        elif quantity < 0:
            raise ValueError("Quantity must be >= 0")

        
        self.recepient = recepient
        self.gift_group = gift_group
        self.gift = gift
        self.quantity = quantity


    def getRecepient(self):
        return self.recepient

    def getGroup(self):
        return self.gift_group

    def getGift(self):
        return self.gift

    def setQuantity(self, quantity):
        if quantity >= 0:
            self.quantity = quantity

    def getQuantity(self):
        return self.quantity



class WishFulfilled(Base):
    __tablename__ = 'wish_fulfilled'

    fulfiller_id = Column(Integer, ForeignKey('person.person_id'), primary_key=True)
    recepient_id = Column(Integer, ForeignKey('person.person_id'), primary_key=True)
    gift_group_id = Column(Integer, ForeignKey('gift_group.gift_group_id'), primary_key=True)
    gift_id = Column(Integer, ForeignKey('gift.gift_id'), primary_key=True)
    quantity = Column(Integer, nullable=False)

    # Definition of ForeignKey
    # Done this way, because specifying them separately as ForeignKey
    # would not have the intended effect. 
    __table_args__  = (ForeignKeyConstraint(['recepient_id', 'gift_group_id', 'gift_id'], ['wish.recepient_id', 'wish.gift_group_id', 'wish.gift_id']), )

    fulfiller = relationship('Person', back_populates='fulfilments', foreign_keys='WishFulfilled.fulfiller_id')
    recepient = relationship('Person', foreign_keys='WishFulfilled.recepient_id')
    gift_group = relationship('Group', back_populates='wishes')
    gift = relationship('Gift', back_populates='fulfilments')
    

    def __init__(self, fulfiller, recepient, gift_group, gift, quantity):
        if fulfiller == None or recepient == None or gift_group == None or gift == None or quantity == None:
            raise ValueError("No argument to Wish can be None.")
        elif quantity < 0:
            raise ValueError("Quantity must be >= 0")

        self.fulfiller = fulfiller
        self.recepient = recepient
        self.gift_group = gift_group
        self.gift = gift
        self.quantity = quantity

    def getFulfiller(self):
        return self.fulfiller

    def getRecepient(self):
        return self.recepient

    def getGroup(self):
        return self.gift_group

    def getGift(self):
        return self.gift

    def setQuantity(self, quantity):
        if quantity >= 0:
            self.quantity = quantity

    def getQuantity(self):
        return self.quantity

    
