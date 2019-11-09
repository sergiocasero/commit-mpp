package com.sergiocasero.commit.navigator

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class Navigator {
    actual fun openSpeakerTwitter(twitterUser: String) {
        val appUrl = NSURL(string = "twitter://user?screen_name=$twitterUser")
        val webUrl = NSURL(string = "https://twitter.com/$twitterUser")

        val app = UIApplication.sharedApplication

        when (app.canOpenURL(appUrl)) {
            true -> app.openURL(appUrl)
            false -> app.openURL(webUrl)
        }
    }

    actual fun navigateToSlotDetail(slotId: Long) {
    }
}
