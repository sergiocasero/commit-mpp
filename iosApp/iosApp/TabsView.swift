//
//  TabsView.swift
//  iosApp
//
//  Created by Daniel Llanos Muñoz on 08/11/2019.
//

import Foundation

class TabsView: NSObject {
    let index: Int
    let name: String
    let id: Int64
    
    init(index: Int, name: String, id: Int64){
        self.index = index
        self.name = name
        self.id = id
    }
    
}
