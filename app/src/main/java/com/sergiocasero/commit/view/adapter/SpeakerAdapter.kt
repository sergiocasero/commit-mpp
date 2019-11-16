package com.sergiocasero.commit.view.adapter

import android.view.View
import com.sergiocasero.commit.R
import com.sergiocasero.commit.common.model.Speaker
import com.sergiocasero.commit.extension.hideMe
import com.sergiocasero.commit.extension.loadAndRound
import com.sergiocasero.commit.extension.showMe
import kotlinx.android.synthetic.main.item_slot.view.*

class SpeakerAdapter(private val onTwitterClick: (Speaker) -> Unit) : RootAdapter<Speaker>() {

    override val itemLayoutId: Int = R.layout.item_slot
    override fun viewHolder(view: View): RootViewHolder<Speaker> = SpeakerViewHolder(view) { onTwitterClick(items[it]) }

    class SpeakerViewHolder(itemView: View, onTwitterClick: (Int) -> Unit) : RootViewHolder<Speaker>(itemView) {

        init {
            itemView.twitter.setOnClickListener { onTwitterClick(adapterPosition) }
        }

        override fun bind(model: Speaker) {
            itemView.name.text = model.name
            itemView.description.text = model.description
            itemView.avatar.loadAndRound(model.avatar)

            when (model.twitterAccount) {
                null -> itemView.twitter.hideMe()
                else -> itemView.twitter.showMe()
            }
        }
    }
}
