//
//  Person.swift
//  WishList
//
//  Created by Håkon Strandlie on 26.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit

class Person {
    
    //MARK: Properties
    var personID: Int?
    var firstName: String
    var lastName: String
    var email: String?
    var phoneNr: String?
    var pictureURL: URL?
   
    
    //MARK: Initialization
    init?(firstName: String, lastName: String) {
        guard (!(firstName.isEmpty) || lastName.isEmpty) else {
            return nil
        }
        self.firstName = firstName
        self.lastName = lastName
        
    }
    
    func setFirstName(firstName: String) {
        guard !(firstName.isEmpty) else {
            return
        }
        self.firstName = firstName
    }
    
    func getFirstName() -> String {
        return self.firstName
    }
    
    func setLastName(lastName: String) {
        guard !(lastName.isEmpty) else {
            return
        }
        self.lastName = lastName
    }
    
    
    
    
    
    
}
