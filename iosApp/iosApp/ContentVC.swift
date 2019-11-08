//
//  ContentVC.swift
//  PageViewControllerWithTabs
//
//  Created by Leela Prasad on 22/03/18.
//  Copyright © 2018 Leela Prasad. All rights reserved.
//

import UIKit
import app
import MaterialComponents

class ContentVC: UIViewController , UICollectionViewDataSource, UICollectionViewDelegate, TalksView{
    
    var pageIndex: Int = 0
    var trackId: Int64 = 0
    
    @IBOutlet weak var slotsView: UICollectionView!
    
     var slots : [CommonSlot] = []
    
    private lazy var presenter: TalksListPresenter = TalksListPresenter(
        repository: CommonClientRepository(local: CommonLocalDataSource(), remote: CommonRemoteDataSource()),
        navigator: Navigator(),
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor())
 
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attach()
    }
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
    }
    
    func obtainTrackId() -> Int64 {
        return trackId
    }
    
    func showSlots(slots: [CommonSlot]) {
        self.slots = slots
        self.slotsView.reloadData()
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return slots.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "SlotCell", for: indexPath) as! SlotCell
        
        let slot = self.slots[indexPath.row]
        
        cell.cornerRadius = 8
        cell.setShadowElevation(ShadowElevation(rawValue: 6), for: .normal)
        cell.setShadowColor(UIColor.colorFromHex("#212121"), for: .normal)
        
        cell.setBorderColor(UIColor.colorFromHex("#212121"), for: .normal)
        cell.setBorderWidth(1, for: .normal)
        
        cell.startDate.text = slot.start
        cell.endDate.text = slot.end
        cell.title.text  = slot.contents?.title
        cell.speakers.text = slot.contents?.speakers.map({$0.name}).joined(separator: ", ")
        
        return cell
    }
    
    
}
