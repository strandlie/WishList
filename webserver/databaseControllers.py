from sqlalchemy.orm import sessionmaker

import dataModel



class PersonDatabaseController():
    def create(person):

    def update(person):

    def retrieve(person_id):

    def retrieveAll():

    def delete(person):

    


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

