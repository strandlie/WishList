//
//  GiftGroup.swift
//  WishList
//
//  Created by Håkon Strandlie on 26.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit

class GiftGroup: NSObject {
    
    //MARK: Properties
    var name: String
    var photo: UIImage?
    var members = [Person]()
 
    init?(name: String, photo:UIImage?, members: [Person]) {
        guard !(name.isEmpty) else {
            return nil
        }
        
        self.name = name
        self.photo = photo
        self.members.append(contentsOf: members)
        
        super.init()
    }
    
    func addMember(_ person : Person) {
        guard !(self.members.contains(person)) else {
            return
        }
       self.members.append(person)
    }
    
    func getMember(name: String) -> [Person] {
        var persons = [Person]()
        for p in self.members {
            if (p.getName() == name) && (!persons.contains(p)) {
                    persons.append(p)
                }
            }
        return persons
    }
}
