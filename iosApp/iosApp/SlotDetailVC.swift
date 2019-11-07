//
//  SlotDetailVC.swift
//  iosApp
//
//  Created by Sergio Casero Hernández on 07/11/2019.
//

import UIKit
import app
import MaterialComponents

class SlotDetailVC: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, SlotDetailView {
    
    @IBOutlet weak var toolbar: UINavigationBar!
    
    @IBOutlet weak var time: UILabel!
    
    @IBOutlet weak var info: UITextView!
    
    @IBOutlet weak var speakersList: UICollectionView!
    
    private let fav = MDCFloatingButton()
    
    private let progress = MDCActivityIndicator()
    
    private lazy var presenter = SlotDetailPresenter(
        repository: CommonClientRepository(
            local: CommonLocalDataSource(),
            remote: CommonRemoteDataSource()
        ),
        navigator: Navigator(),
        view: self,
        errorHandler: IosErrorHandler(),
        executor: Executor()
    )
    
    var speakers: [CommonSpeaker] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initializeUI()
        registerListeners()
        presenter.attach()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func initializeUI() {
        time.textAlignment = .center
        
        let plusImage = UIImage(named:"heart")?.withRenderingMode(.alwaysTemplate)

        fav.setImage(plusImage, for: .normal)
        fav.backgroundColor = UIColor.gray
        fav.setImageTintColor(UIColor.white, for: .normal)
        fav.sizeToFit()
        
        let screen = UIScreen.main.bounds
        fav.center = CGPoint(x: screen.width - 50, y: screen.height - 50)
        
        time.layer.cornerRadius = 8
        
        progress.sizeToFit()
        progress.center = CGPoint(x: screen.width / 2, y : screen.height / 2)

        view.addSubview(fav)
        view.addSubview(progress)
    }
    
    func registerListeners() {
        fav.addTarget(self, action: #selector(pressed), for: .touchUpInside)

    }
    
    @objc func pressed(sender: UIButton) {
        presenter.onFavClick()
    }
    
    func showProgress() {
        progress.startAnimating()
    }
    
    func hideProgress() {
        progress.stopAnimating()
    }
    
    func showError(error: String) {
        
    }
    
    func getSlotId() -> Int64 {
        return 387404009
    }
    
    func showSlot(slot: CommonSlot) {
        let contents = slot.contents
    
        
        time.text = slot.start + " - " + slot.end
        toolbar.topItem?.title = contents?.title
        info.text = contents?.description
        speakers = contents?.speakers ?? []
        
        print(speakers)
        
        speakersList.reloadData()
    }
    
    func showFavUI(isFav: Bool) {
        let color = isFav ? UIColor.purple : UIColor.white
        let background = isFav ? UIColor.yellow : UIColor.gray
        
        fav.setImageTintColor(color, for: .normal)
        fav.backgroundColor = background
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return speakers.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "speaker", for: indexPath) as! SpeakersViewCell
        
        let speaker = self.speakers[indexPath.row]
        
        print(speaker)
        
        cell.cornerRadius = 8
        cell.setShadowElevation(ShadowElevation(rawValue: 6), for: .normal)
        cell.setShadowColor(UIColor.colorFromHex("#212121"), for: .normal)
        
        cell.setBorderColor(UIColor.colorFromHex("#212121"), for: .normal)
        cell.setBorderWidth(1, for: .normal)
        
        
        
        
        let url = URL(string: speaker.avatar)
        let data = try? Data(contentsOf: url!)
        cell.avatar.image = UIImage(data: data!)
        cell.avatar.contentMode = .scaleAspectFit
        
        cell.avatar.layer.cornerRadius = cell.avatar.frame.size.width / 2
        cell.avatar.clipsToBounds = true
        
        cell.name.text = speaker.name
        
        cell.bio.text = speaker.description
        cell.bio.numberOfLines = 50
        
        if speaker.twitterAccount != nil {
            let bounds = cell.bounds
            
            let twitter = MDCFloatingButton()
            twitter.setImage(UIImage(named:"twitter")?.withRenderingMode(.alwaysTemplate), for: .normal)
            twitter.backgroundColor = UIColor.colorFromHex("#1CA1F2")
            twitter.setImageTintColor(UIColor.white, for: .normal)
            twitter.sizeToFit()
            twitter.tag = indexPath.row
            twitter.addTarget(self, action: #selector(twitterClick), for: .touchUpInside)
            twitter.center = CGPoint(x: bounds.width - 50, y: 90)
            
            
            cell.addSubview(twitter)
        }
        
        return cell
    }
    
    @objc func twitterClick(sender: UIButton) {
        presenter.onSpeakerTwitterClick(speaker: speakers[sender.tag])
    }
}
