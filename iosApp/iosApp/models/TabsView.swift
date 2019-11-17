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
