package com.atritripathi.techfest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atritripathi.techfest.R
import com.atritripathi.techfest.models.Event
import kotlinx.android.synthetic.main.list_item_view.view.*

class EventsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {

        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return (oldItem.title == newItem.title
                    && oldItem.body == newItem.body
                    && oldItem.time == newItem.time)
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_view,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Event>) {
        differ.submitList(list)
    }

    class EventViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Event) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            tv_event_title.text == item.title
            tv_event_body.text == item.body
            tv_event_time.text == item.time

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Event)
    }
}
