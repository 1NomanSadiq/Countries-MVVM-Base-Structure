package com.gsc.app.utils.misc

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter


class NoFilterArrayAdapter<T>(
    context: Context, textViewResourceId: Int,
    objects: List<T>
) : ArrayAdapter<T>(context, textViewResourceId, objects) {
    private val filter: Filter = KNoFilter()
    var items: List<T>
    override fun getFilter(): Filter {
        return filter
    }

    init {
        items = objects
    }

    private inner class KNoFilter : Filter() {
        override fun performFiltering(arg0: CharSequence?): FilterResults {
            val result = FilterResults()
            result.values = items
            result.count = items.size
            return result
        }

        override fun publishResults(arg0: CharSequence?, arg1: FilterResults?) {
            notifyDataSetChanged()
        }
    }
}