package com.sergiocasero.commit.common.navigator

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

class IosNavigator(private val viewController: UIViewController): Navigator {
    override fun openSpeakerTwitter(twitterUser: String) {
        val appUrl = NSURL(string = "twitter://user?screen_name=$twitterUser")
        val webUrl = NSURL(string = "https://twitter.com/$twitterUser")

        val app = UIApplication.sharedApplication

        when (app.canOpenURL(appUrl)) {
            true -> app.openURL(appUrl)
            false -> app.openURL(webUrl)
        }
    }

    override fun navigateToSlotDetail(slotId: Long) {
        val slotViewController = viewController.storyboard?.instantiateViewControllerWithIdentifier("SlotDetailVC") //Need cast
        slotViewController?.let {
            viewController.presentViewController(it, true) {}
        }
    }
}
