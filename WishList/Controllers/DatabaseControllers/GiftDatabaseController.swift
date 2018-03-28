//
//  GiftDatabaseController.swift
//  WishList
//
//  Created by Håkon Strandlie on 28.03.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit
import OHMySQL
import SwiftWebSocket

class GiftDatabaseController: DatabaseCRUD {
    func create(object: AnyObject) {
        let login = loginData()
        let ws = WebSocket(login.dbServerName)
        print(ws.description)
        let user = OHMySQLUser(userName: login.userName, password: login.password, serverName: login.dbServerName, dbName: login.dbName, port: UInt(login.portNr), socket: ws.description)
    }
    
    func retrieve() -> AnyObject? {
        return nil
    }
    
    func update(object: AnyObject) {
        return
    }
    
    func delete(object: AnyObject) {
        return
    }
    
    func objectIsGift(object: AnyObject) -> Gift? {
        guard let gift = object as? Gift else {
            return nil
        }
        return gift
    }
}



    

