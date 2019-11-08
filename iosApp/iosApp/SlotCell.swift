//
//  SlotCell.swift
//  iosApp
//
//  Created by Daniel Llanos Muñoz on 08/11/2019.
//

import Foundation
import UIKit
import MaterialComponents

class SlotCell: MDCCardCollectionCell {
    @IBOutlet weak var startDate: UILabel!
    @IBOutlet weak var endDate: UILabel!
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var speakers: UILabel!
}
