package com.sergiocasero.commit.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sergiocasero.commit.view.FavSlotsActivity
import com.sergiocasero.commit.view.SlotDetailActivity

actual class Navigator(private val context: Context) {

    actual fun openSpeakerTwitter(twitterUser: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$twitterUser"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    actual fun navigateToSlotDetail(slotId: Long) {
        val intent = SlotDetailActivity.getCallingIntent(context, slotId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    actual fun navigateToFavSlotScreen() {
        val intent = FavSlotsActivity.getCallingIntent(context)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


}
