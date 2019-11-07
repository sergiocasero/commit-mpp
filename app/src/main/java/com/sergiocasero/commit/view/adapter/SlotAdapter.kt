package com.sergiocasero.commit.view.adapter

import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.extension.hideMe
import com.sergiocasero.commit.extension.showMe
import kotlinx.android.synthetic.main.item_track.view.*

class SlotAdapter(onTrackClicked: (Slot) -> Unit = {}) : RootAdapter<Slot>(onItemClickListener = onTrackClicked) {

    override val itemLayoutId: Int = R.layout.item_track

    override fun viewHolder(view: View): RootViewHolder<Slot> = ViewHolder(view)

    private inner class ViewHolder(view: View) : RootViewHolder<Slot>(view) {
        override fun bind(model: Slot) {
            model.contents?.let {
                if (it.title != "") {
                    itemView.title.text = it.title
                    itemView.title.showMe()
                } else {
                    itemView.title.hideMe()
                }

                if (it.speakers.isNotEmpty()) {
                    itemView.speakers.text = it.speakers.joinToString(separator = ",") { it.name }
                    itemView.speakers.showMe()
                } else {
                    itemView.speakers.hideMe()
                }
            }
            itemView.startDate.text = model.start
            itemView.endDate.text = model.end
        }
    }

}