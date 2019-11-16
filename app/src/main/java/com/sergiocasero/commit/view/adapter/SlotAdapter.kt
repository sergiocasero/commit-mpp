package com.sergiocasero.commit.view.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.extension.hideMe
import com.sergiocasero.commit.extension.showMe
import kotlinx.android.synthetic.main.item_slot.view.*
import kotlinx.android.synthetic.main.item_track.view.*

class SlotAdapter(onTrackClicked: (Slot) -> Unit = {}) : RootAdapter<Slot>(onItemClickListener = onTrackClicked) {

    override val itemLayoutId: Int = R.layout.item_track

    override fun viewHolder(view: View): RootViewHolder<Slot> = ViewHolder(view)

    private inner class ViewHolder(view: View) : RootViewHolder<Slot>(view) {
        override fun bind(model: Slot) {
            model.contents?.let {
                with(itemView) {
                    if (it.title != "") {
                        title.text = it.title
                        title.showMe()
                    } else {
                        title.hideMe()
                    }

                    if (it.speakers.isNotEmpty()) {
                        speakers.text = it.speakers.joinToString(separator = ",") { it.name }
                        speakers.showMe()
                    } else {
                        speakers.hideMe()
                    }
                    when (it.type) {
                        "EXTENDED" -> {
                            startDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                            slot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey_500))
                        }
                        "BREAK" -> {
                            startDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                            slot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey_500))
                        }
                        else -> {
                            startDate.setTextColor(ContextCompat.getColor(context, R.color.red_commit))
                            slot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                        }

                    }

                }
            }
            itemView.startDate.text = model.start
            itemView.endDate.text = model.end
        }
    }

}