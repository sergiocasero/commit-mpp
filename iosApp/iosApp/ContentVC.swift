//
//  ContentVC.swift
//  PageViewControllerWithTabs
//
//  Created by Leela Prasad on 22/03/18.
//  Copyright © 2018 Leela Prasad. All rights reserved.
//

import UIKit
import app

class ContentVC: UIViewController, TalksView {
    
    var pageIndex: Int = 0
    var trackId: Int64 = 0

    @IBOutlet weak var nameLabel: UILabel!
    
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
        print("trackId: \(trackId)")
        print("contentVCIndex: \(pageIndex)")
    }
    
}
