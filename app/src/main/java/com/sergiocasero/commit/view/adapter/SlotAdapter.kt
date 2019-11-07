package com.sergiocasero.commit.view.adapter

import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Slot
import kotlinx.android.synthetic.main.item_track.view.*

class SlotAdapter(onTrackClicked: (Slot) -> Unit = {}) : RootAdapter<Slot>(onItemClickListener = onTrackClicked) {

    override val itemLayoutId: Int = R.layout.item_track

    override fun viewHolder(view: View): RootViewHolder<Slot> = ViewHolder(view)

    private inner class ViewHolder(view: View) : RootViewHolder<Slot>(view) {
        override fun bind(model: Slot) {
            itemView.title.text = model.contents?.title ?: ""
            itemView.speakers.text = model.contents?.speakers?.joinToString(separator = ",") { it.name } ?: ""
            itemView.startDate.text = model.start
            itemView.endDate.text = model.end
        }
    }

}