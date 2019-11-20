import UIKit
import app
import MaterialComponents

class FavSlotsVC: UIViewController , UICollectionViewDataSource, UICollectionViewDelegate, FavsSlotsView {
    
    var pageIndex: Int = 0
    var trackId: Int64 = 0
    
    @IBOutlet weak var slotsView: UICollectionView!
    
    var slots : [CommonSlot] = []
    
    private lazy var presenter: FavSlotsPresenter = FavSlotsPresenter(
        repository: CommonClientRepository(local: LocalDataSource(), remote: CommonRemoteDataSource()),
        navigator: Navigator(viewController: self),
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor())
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.slotsView.delegate = self
        presenter.attach()
        self.title = "Favoritos"
    }
    
    func showRetry(error: String, f: @escaping () -> Void) {
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
    
    func showProgress() {
        
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
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
        
        cell.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tap(_:))))
        
        cell.dropShadow()
        cell.cornerRadius = 8
        
        cell.startDate.text = slot.start
        cell.endDate.text = slot.end
        
        cell.title.text  = slot.contents?.title
        cell.title.numberOfLines = 0
        cell.title.sizeToFit()
        
        cell.speakers.numberOfLines = 0
        cell.speakers.text = slot.contents?.speakers.map({$0.name}).joined(separator: ", ")
        cell.speakers.sizeToFit()
        
        if slot.contents?.type == "BREAK" || slot.contents?.type == "EXTEND" {
            cell.backgroundColor = UIColor.lightGray
        } else {
            cell.backgroundColor = UIColor.white
        }
        
        cell.sizeToFit()
        
        return cell
    }
    
    @objc func tap(_ sender: UITapGestureRecognizer) {
        let location = sender.location(in: self.slotsView)
        let indexPath = self.slotsView.indexPathForItem(at: location)
        if let index = indexPath {
            presenter.onSlotClicked(slot: slots[index.item])
        }
    }
    
    func navigateToSlotDetail(slotId: Int64) {
        let slotDetailVC = self.storyboard?.instantiateViewController(withIdentifier: "SlotDetailVC") as! SlotDetailVC
        slotDetailVC.slotId = slotId
        self.navigationController?.pushViewController(slotDetailVC, animated: true)
    }
    
}

