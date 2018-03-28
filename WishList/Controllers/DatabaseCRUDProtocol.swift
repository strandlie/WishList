//
//  DatabaseCRUDProtocol.swift
//  WishList
//
//  Created by Håkon Strandlie on 28.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit

protocol DatabaseCRUD {
    func create(object: AnyObject)
    func retrieve() -> AnyObject?
    func update(object: AnyObject)
    func delete(object: AnyObject)
    
}

struct loginData {
    let dbServerName = "wishlistdb.c0lilbtiidoe.eu-central-1.rds.amazonaws.com"
    let dbName = "WishList"
    let userName = "haakonstrandlie"
    let password = "m6Z-h6d-zhV-uyu"
    let portNr = Int32(3306)
}
