//
//  DataViewController.swift
//  WishList
//
//  Created by Håkon Strandlie on 06.01.2018.
//  Copyright © 2018 Liferoom. All rights reserved.
//

import UIKit

class GroupViewController: UIViewController {

    @IBOutlet weak var dataLabel: UILabel!


    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        //self.dataLabel!.text = dataObject
    }


}

