//
//  Gift.swift
//  WishList
//
//  Created by Håkon Strandlie on 27.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit

class Gift: NSObject {
   
    
    
    //MARK: Properties
    var id: Int
    var title: String
    var description: String?
    var url: URL?
    var wantedBy: Person
    var boughtBy: [Person]

    
    //MARK: Initialization
    init?(id: Int, title: String, wantedBy: Person, boughtBy: [Person], description: String?, url: String?) {
       
        guard !(title.isEmpty) else {
            return nil;
        }
        
        self.id = id
        self.title = title
        self.description = description
        self.wantedBy = wantedBy
        self.boughtBy = boughtBy
        
        super.init()
        
        guard let urlPath = url else {
            self.url = nil
            return
        }
        self.url = URL(string: urlPath)
    }
    
    func setWantedBy(person: Person) {
        self.wantedBy = person
    }
    
    func getWantedBy() -> Person {
        return self.wantedBy
    }
    
    func addBoughtBy(persons: [Person]) {
        for person in persons {
            
        }
    }
    
    
}
