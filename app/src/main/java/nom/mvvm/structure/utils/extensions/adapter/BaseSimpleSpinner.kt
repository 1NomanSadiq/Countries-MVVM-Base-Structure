package nom.mvvm.structure.utils.extensions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nom.mvvm.structure.databinding.ItemSimpleSpinnerBinding

abstract class BaseSimpleSpinner<T> : BaseAdapter() {

    private var data: List<T> = emptyList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = if (convertView == null) {
            val inflater = LayoutInflater.from(parent?.context)
            ItemSimpleSpinnerBinding.inflate(inflater, parent, false)
        } else {
            ItemSimpleSpinnerBinding.bind(convertView)
        }

        bindData(binding, getItem(position))

        return binding.root
    }

    override fun getItem(position: Int): T {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    abstract fun bindData(binding: ItemSimpleSpinnerBinding, item: T)

    fun setData(newData: List<T>) {
        data = newData
        notifyDataSetChanged()
    }
}