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
    
    @IBOutlet weak var nameLabel: UILabel!
    
    private lazy var presenter: TalksListPresenter = TalksListPresenter(
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor())
 
    var pageIndex: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attach()
    }
    
    func showProgress() {
        TestIosTestDani()
    }
    
    func hideProgress() {
    
    }
    
    func showError(error: String) {
    
    }
    
    func showError(errorId: Int32) {
    
    }
    
    func showMessage(message: String) {
    
    }
    
    func showMessage(messageId: Int32) {
    
    }
    
    func showTrackId(id: String) {
        print("ID: " + id)
        nameLabel.text = id
    }
    
    func obtainTrackId() -> Int32 {
        return Int32(pageIndex)
    }
    
}
