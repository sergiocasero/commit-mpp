//
//  TalkListViewController.swift
//  iosApp
//
//  Created by Daniel Llanos Muñoz on 23/10/2019.
//
import UIKit

class TalkListViewContorller : UICollectionViewController, UICollectionViewDelegateFlowLayout {
    
    var trackId: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Track  \(trackId)"
    }
    
}
