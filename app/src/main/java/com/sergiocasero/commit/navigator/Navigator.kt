package com.sergiocasero.commit.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class Navigator(private val context: Context) {


    actual fun openSpeakerTwitter(twitterUser: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$twitterUser"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }

}
