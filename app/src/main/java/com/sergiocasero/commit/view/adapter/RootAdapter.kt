package com.sergiocasero.commit.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * RootAdapter
 */
abstract class RootAdapter<T>(protected val items: MutableList<T> = mutableListOf(),
                              private val onItemClickListener: (T) -> Unit = {},
                              private val onLongClickListener: (T) -> Unit = {}
) : RecyclerView.Adapter<RootAdapter.RootViewHolder<T>>() {

    abstract val itemLayoutId: Int

    abstract fun viewHolder(view: View): RootViewHolder<T>

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)

        val viewHolder = viewHolder(view)
        viewHolder.onItemClickListener = { onItemClickListener(items[it]) }
        viewHolder.onLongClickListener = { onLongClickListener(items[it]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RootViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun remove(item: T) {
        if (items.contains(item)) {
            val index = items.indexOf(item)
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun update(item: T) {
        if (items.contains(item)) {
            val index = items.indexOf(item)
            items[index] = item
            notifyItemChanged(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addAll(newItems: List<T>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun replace(newItems: List<T>) {
        clear()
        addAll(newItems)
    }

    fun indexOf(item: T) = items.indexOf(item)

    abstract class RootViewHolder<in T>(itemView: View,
                                        var onItemClickListener: (Int) -> Unit = {},
                                        var onLongClickListener: (Int) -> Unit = {}) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { onItemClickListener(adapterPosition) }
            itemView.setOnLongClickListener {
                onLongClickListener(adapterPosition)
                true
            }
        }

        abstract fun bind(model: T)
    }
}
