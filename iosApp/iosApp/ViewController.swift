//
//  ViewController.swift
//  PageViewControllerWithTabs
//
//  Created by Leela Prasad on 22/03/18.
//  Copyright © 2018 Leela Prasad. All rights reserved.
//

import UIKit
import app

class ViewController: UIViewController, HomeView {
    
    @IBOutlet weak var menuBarView: MenuTabsView!
    
    var tabs : [String] = []
    
    private lazy var presenter: HomePresenter = HomePresenter(
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor())
    
    var currentIndex: Int = 0
    var pageController: UIPageViewController!

    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attach()
    }
    
    func presentPageVCOnView() {
        self.pageController = storyboard?.instantiateViewController(withIdentifier: "PageControllerVC") as! PageControllerVC
        self.pageController.view.frame = CGRect.init(x: 0, y: menuBarView.frame.maxY, width: self.view.frame.width, height: self.view.frame.height - menuBarView.frame.maxY)
        self.addChildViewController(self.pageController)
        self.view.addSubview(self.pageController.view)
        self.pageController.didMove(toParentViewController: self)
        
    }
    
    //Present ViewController At The Given Index
    
    func viewController(At index: Int) -> UIViewController? {
        
        if((self.menuBarView.dataArray.count == 0) || (index >= self.menuBarView.dataArray.count)) {
            return nil
        }
        
        let contentVC = storyboard?.instantiateViewController(withIdentifier: "ContentVC") as! ContentVC
        contentVC.pageIndex = index
        currentIndex = index
        return contentVC
        
    }
    
}

extension ViewController: MenuBarDelegate {

    func menuBarDidSelectItemAt(menu: MenuTabsView, index: Int) {

        // If selected Index is other than Selected one, by comparing with current index, page controller goes either forward or backward.
        
        if index != currentIndex {
            if index > currentIndex {
                self.pageController.setViewControllers([viewController(At: index)!], direction: .forward, animated: true, completion: nil)
            }else {
                self.pageController.setViewControllers([viewController(At: index)!], direction: .reverse, animated: true, completion: nil)
            }

            menuBarView.collView.scrollToItem(at: IndexPath.init(item: index, section: 0), at: .centeredHorizontally, animated: true)

        }

    }

}


extension ViewController: UIPageViewControllerDataSource, UIPageViewControllerDelegate {
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        
        var index = (viewController as! ContentVC).pageIndex
        
        if (index == 0) || (index == NSNotFound) {
            return nil
        }
        
        index -= 1
        return self.viewController(At: index)
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController? {
        
        var index = (viewController as! ContentVC).pageIndex
        
        if (index == tabs.count) || (index == NSNotFound) {
            return nil
        }
        
        index += 1
        return self.viewController(At: index)
        
    }
   
    func pageViewController(_ pageViewController: UIPageViewController, didFinishAnimating finished: Bool, previousViewControllers: [UIViewController], transitionCompleted completed: Bool) {
        
        if finished {
            if completed {
                let cvc = pageViewController.viewControllers!.first as! ContentVC
                let newIndex = cvc.pageIndex + 1
                menuBarView.collView.selectItem(at: IndexPath.init(item: newIndex, section: 0), animated: true, scrollPosition: .centeredVertically)
                menuBarView.collView.scrollToItem(at: IndexPath.init(item: newIndex, section: 0), at: .centeredHorizontally, animated: true)
            }
        }
        
    }
    
    func showProgress() {
        
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
    
    func showTracks(tracks: Int32) {
        tabs.removeAll()
        for n in 0...tracks {
            tabs.append("Track \(n)")
        }
        
        menuBarView.dataArray = tabs
        menuBarView.isSizeToFitCellsNeeded = true
        menuBarView.collView.backgroundColor = UIColor(red:0.13, green:0.13, blue:0.13, alpha:1.0)
        
        presentPageVCOnView()
        
        menuBarView.menuDelegate = self
        pageController.delegate = self
        pageController.dataSource = self
        
        menuBarView.collView.selectItem(at: IndexPath.init(item: 0, section: 0), animated: true, scrollPosition: .centeredVertically)
        pageController.setViewControllers([viewController(At: 0)!], direction: .forward, animated: true, completion: nil)
        
    }
    
}
