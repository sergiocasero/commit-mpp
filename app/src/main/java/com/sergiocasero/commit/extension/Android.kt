package com.sergiocasero.commit.extension

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sergiocasero.commit.R

/**
 * ContextExtensions
 */

/**
 * Context
 * */
fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(textId: Int, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, textId, length).show()
}

fun Context.toPx(dp: Int): Int = resources.getDimensionPixelSize(dp)


/**
 * Fragments
 * */
fun Fragment.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(activity, text, length).show()
}

fun Fragment.toast(textId: Int, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(activity, textId, length).show()
}

/**
 * PreventMonkeyClicks.
 */
fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action.invoke()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

/**
 * ViewExtensions.
 */
fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

/**
 * View
 * */
fun View.hideMe(gone: Boolean = true) {
    this.visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun Context.snackbar(container: View,
                     message: String,
                     action: Int = R.string.retry,
                     backgroundColor: Int = R.color.red_800,
                     showAction: Boolean = true,
                     length: Int = Snackbar.LENGTH_INDEFINITE,
                     retryCallback: () -> Unit = {}) {
    val color = ContextCompat.getColor(this, R.color.white)
    val snackbar = Snackbar.make(container, message, length)
        .setActionTextColor(color)

    if (showAction) {
        snackbar.setAction(action) { retryCallback() }
    }

    snackbar.view.setBackgroundResource(backgroundColor)
    snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(color)
    snackbar.show()
}
