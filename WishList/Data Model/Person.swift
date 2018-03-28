//
//  Person.swift
//  WishList
//
//  Created by Håkon Strandlie on 26.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit
import OHMySQL

class Person: NSObject  {
    
    //MARK: Properties
    var personID: Int?
    var name: String
   
    
    //MARK: Initialization
    init?(name: String) {
        guard !(name.isEmpty) else {
            return nil
        }
        self.name = name
        super.init()
    }
    
    func setName(name: String) {
        guard !(name.isEmpty) else {
            return
        }
        self.name = name
    }
    
    func getName() -> String {
        return self.name
    }
    
    
    
    
}
