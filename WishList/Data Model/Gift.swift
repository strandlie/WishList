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
    var wantedBy: Person
    var boughtBy: [Person]
    var title: String
    var giftID: Int?
    var giftDescription: String?
    var url: URL?
    
    //MARK: Initialization
    init?(title:String, wantedBy: Person, boughtBy: [Person], description:String?, url: String?) {
       
        guard !(title.isEmpty) else {
            return nil;
        }
        
        self.wantedBy = wantedBy
        self.title = title
        self.giftDescription = description
        self.boughtBy = boughtBy
        
        super.init()
        
        guard let urlPath = url else {
            self.url = nil
            return
        }
        self.url = URL(string: urlPath)
    }
    
    
}
