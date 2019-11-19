import UIKit
import app
import MaterialComponents

class HomeVC: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout, HomeView {

    @IBOutlet weak var menuBarView: MenuTabsView!
    
    @IBOutlet weak var daysMenu: UICollectionView!
    
    var tabs : [TabsView] = []
    
    var days: [DayView] = []
    
    private lazy var presenter: HomePresenter = HomePresenter(
        repository: CommonClientRepository(
            local: LocalDataSource(),
            remote: CommonRemoteDataSource()
        ),
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor())
    
    var currentIndex: Int = 0
    var pageController: UIPageViewController!

    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attach()
    }
    
    func showRetry(error: String, f: @escaping () -> Void) {
        print("hello")
        let message = MDCSnackbarMessage()
        message.text = error
        
        let action = MDCSnackbarMessageAction()
        let actionHandler = {() in
            f()
        }
        action.handler = actionHandler
        action.title = "Retry"
        message.action = action
        
        MDCSnackbarManager.show(message)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
       return days.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "dayCell", for: indexPath) as! DayViewCell
        
        let day = self.days[indexPath.row]
        
        cell.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tap(_:))))
        cell.dayName.text = day.title
        cell.dayName.lineBreakMode = .byClipping
        cell.dayIcon.image = UIImage(named: "calendar")
        
        cell.dayName.lineBreakMode = .byClipping
        cell.dayIcon.image = UIImage(named: "calendar")?.withRenderingMode(.alwaysTemplate)
        if(day.selected){
            cell.dayName.textColor = UIColor.white
            cell.dayIcon.tintColor = UIColor.white
        } else {
            cell.dayName.textColor = UIColor.gray
            cell.dayIcon.tintColor = UIColor.gray
        }
    
        return cell
    }
    
    @objc func tap(_ sender: UITapGestureRecognizer) {
        let location = sender.location(in: self.daysMenu)
        let indexPath = self.daysMenu.indexPathForItem(at: location)
        for day in days {
            day.selected = false
        }
        if let index = indexPath {
            days[index.item].selected = true
            daysMenu.reloadData()
            presenter.onDaySelected(dayPos: days[index.item].pos)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        
        let totalCellWidth = 80 * collectionView.numberOfItems(inSection: 0)
        let totalSpacingWidth = 10 * (collectionView.numberOfItems(inSection: 0) - 1)
        
        let leftInset = (collectionView.layer.frame.size.width - CGFloat(totalCellWidth + totalSpacingWidth)) / 2
        let rightInset = leftInset
        
        return UIEdgeInsets(top: 0, left: leftInset, bottom: 0, right: rightInset)
        
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let day = days[indexPath.item]
        print(day)
        presenter.onDaySelected(dayPos: day.pos)
    }
    
    func presentPageVCOnView() {
        self.pageController = storyboard?.instantiateViewController(withIdentifier: "PageControllerVC") as! PageControllerVC
        self.pageController.view.frame = CGRect.init(x: 0, y: menuBarView.frame.maxY, width: self.view.frame.width, height: self.view.frame.height - menuBarView.frame.maxY - daysMenu.frame.height)
        self.addChildViewController(self.pageController)
        self.view.addSubview(self.pageController.view)
        self.pageController.didMove(toParentViewController: self)
    }
    
    //Present ViewController At The Given Index
    
    func viewController(At index: Int) -> UIViewController? {
        if((self.menuBarView.dataArray.count == 0) || (index >= self.menuBarView.dataArray.count)) {
            return nil
        }
        
        let contentVC = storyboard?.instantiateViewController(withIdentifier: "ContentVC") as! SlotsListVC
        contentVC.trackId = Int64(tabs[index].id)
        contentVC.pageIndex = index
        currentIndex = index
        return contentVC
    }
    
    func showDays(days: [DayView]) {
        self.days = days
        self.daysMenu.reloadData()
    }
    
    func showTracks(tracks: [CommonTrackItem]) {
        tabs.removeAll()
        for (index, track) in tracks.enumerated() {
            tabs.append(TabsView(index: index, name: track.name, id: track.id))
        }

        menuBarView.dataArray = tabs
        menuBarView.isSizeToFitCellsNeeded = true
        menuBarView.collView.backgroundColor = UIColor(red:0.13, green:0.13, blue:0.13, alpha:1.0)

        presentPageVCOnView()

        menuBarView.menuDelegate = self
       // pageController.delegate = self
       // pageController.dataSource = self

        menuBarView.collView.selectItem(at: IndexPath.init(item: 0, section: 0), animated: true, scrollPosition: .centeredVertically)
        pageController.setViewControllers([viewController(At: 0)!], direction: .forward, animated: true, completion: nil)
    }
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
    }
    
}

extension HomeVC : MenuBarDelegate {

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



extension HomeVC: UIPageViewControllerDataSource, UIPageViewControllerDelegate {
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        
        var index = (viewController as! SlotsListVC).pageIndex
        
        if (index == 0) || (index == NSNotFound) {
            return nil
        }
        
        index -= 1
        return self.viewController(At: index)
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController? {
        
        var index = (viewController as! SlotsListVC).pageIndex
        
        if (index == tabs.count) || (index == NSNotFound) {
            return nil
        }
        
        index += 1
        return self.viewController(At: index)
        
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, didFinishAnimating finished: Bool, previousViewControllers: [UIViewController], transitionCompleted completed: Bool) {
        
        if finished {
            if completed {
                let cvc = pageViewController.viewControllers!.first as! SlotsListVC
                let newIndex = cvc.pageIndex
                menuBarView.collView.selectItem(at: IndexPath.init(item: newIndex, section: 0), animated: true, scrollPosition: .centeredVertically)
                menuBarView.collView.scrollToItem(at: IndexPath.init(item: newIndex, section: 0), at: .centeredHorizontally, animated: true)
            }
        }
        
    }
    
}
