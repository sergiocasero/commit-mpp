package com.sergiocasero.commit.navigator

expect class Navigator {
    fun openSpeakerTwitter(twitterUser: String)
    fun navigateToSlotDetail(slotId: Long)
    fun navigateToFavSlotScreen()
}
