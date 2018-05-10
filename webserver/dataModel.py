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
    wishes = relationship(Wish, back_populates=Wish.recepient)
    fulfilments = relationship(WishFulfilled, back_populates=WishFulfilled.fulfiller_id)

    def __init__(self, name):
        self.name = name

    def addToGroup(self, group):
        if not isInGroup(group):
            self.groups.append(group)

    def removeFromGroup(self, group):
        if isInGroup(group):
            self.groups.remove(group)

    def isInGroup(self, group):
        return group in self.groups


class Group(Base):
    __tablename__ = 'group'
    
    group_id = Column(Integer, primary_key=True)
    name = Column(String(50), nullable=False)

    # Since Person-Group is a many-to-many relationship, we use the secondary 
    # personInGroup Table to map this relationship
    members = relationship(Person, secondary=personInGroup, back_populates=Person.groups)
    wishes = relationship(Wish, back_populates=Wish.group)

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
    wishes = relationship(Wish, back_populates=Wish.gift)

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



class Wish(Base):
    __tablename__ = 'wish'

    # Has an ID-field for every part of the primary key
    recepient_id = Column(Integer, primary_key=True, ForeignKey(Person.person_id))
    group_id = Column(Integer, primary_key=True, ForeignKey(Group.group_id))
    gift_id = Column(Integer, primary_key=True, ForeignKey(Gift.gift_id))
    quantity = Column(Integer, nullable=False)

    # The relationships are configured from the ForeignKeys above
    recepient = relationship(Person, back_populates=Person.wishes)
    group = relationship(Group, back_populates=Group.wishes)
    gift = relationship(Gift, back_populates=Gift.wishes)


    def __init__(self, recepient, group, gift, quantity):
        if recepient == None or group == None or gift == None or quantity == None:
            raise ValueError("No argument to Wish can be None.")
        elif quantity < 0:
            raise ValueError("Quantity must be >= 0")

        """
        MUST ADD ASSIGNMENT TO THE ID FIELDS HERE"""
        
        self.recepient = recepient
        self.group = group
        self.gift = gift
        self.quantity = quantity


    def getRecepient(self):
        return self.recepient

    def getGroup(self):
        return self.group

    def getGift(self):
        return self.gift

    def setQuantity(self, quantity):
        if quantity >= 0:
            self.quantity = quantity

    def getQuantity(self):
        return self.quantity



class WishFulfilled(Base):
    __tablename__ = 'wish_fulfilled'

    fulfiller_id = Column(Integer, ForeignKey(Person.person_id), primary_key=True)
    recepient_id = Column(Integer, primary_key=True)
    group_id = Column(Integer, primary_key=True)
    gift_id = Column(Integer, primary_key=True)
    quantity = Column(Integer, nullable=False)

    # Relationship for composite foreign key.
    # Done this way, because specifying them separately as ForeignKey
    # would not have the intended effect. 
    __table_args__  = (ForeignKeyConstraint([recepient_id, group_id, gift_id], [Wish.recepient_id, Wish.group_id, Wish.gift_id]), )

    fulfiller = relationship(Person, back_populates=Person.fulfilments)
    

    def __init__(self, )
    """COMPLETE THiS"""
