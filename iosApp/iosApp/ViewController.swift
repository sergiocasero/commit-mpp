import UIKit
import app

class ViewController: UITabBarController {
    
    let TALK_LIST = 12
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var navigationsViewControllers =  [UINavigationController]()
        
        for n in 1...TALK_LIST {
            let trackContorller = TalkListViewContorller(collectionViewLayout: UICollectionViewFlowLayout())
            trackContorller.trackId = n
            let trackNavigationController = UINavigationController(rootViewController: trackContorller)
            trackNavigationController.title = "Track \(n)"
            navigationsViewControllers.append(trackNavigationController)
        }
        
        viewControllers = navigationsViewControllers
        
    }

}
