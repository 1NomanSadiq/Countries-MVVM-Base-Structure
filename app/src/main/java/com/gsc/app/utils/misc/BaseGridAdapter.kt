package com.gsc.app.utils.misc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseGridAdapter<T, B : ViewBinding> : BaseAdapter() {

    private val items: MutableList<T> = mutableListOf()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): T = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: B
        val view: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            binding = inflateLayout(inflater, parent)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as B
        }

        bind(binding, getItem(position))
        return view
    }

    abstract fun bind(binding: B, item: T)
    abstract fun inflateLayout(inflater: LayoutInflater, parent: ViewGroup): B


    fun setData(data: List<T>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }

}