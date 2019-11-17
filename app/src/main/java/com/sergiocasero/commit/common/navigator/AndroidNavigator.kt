package com.sergiocasero.commit.common.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sergiocasero.commit.view.SlotDetailActivity

class AndroidNavigator(private val context: Context): Navigator {

    override fun openSpeakerTwitter(twitterUser: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$twitterUser"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun navigateToSlotDetail(slotId: Long) {
        val intent = SlotDetailActivity.getCallingIntent(context, slotId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}
