package nom.mvvm.structure.utils.extensions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(private val diffUtilCallbackMaker: DiffUtilMaker<T> = { BaseDiffUtilItemCallback() }) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    private val listDiffer by lazy {
        AsyncListDiffer(this, diffUtilCallbackMaker())
    }

    var onItemClickListener: ((Int, T) -> Unit)? = null
    var onLongItemClickListener: ((Int, T) -> Boolean)? = null
    var onBottomReachedListener: ((Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflateLayout(inflater, parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener {
                val finalPosition = holder.absoluteAdapterPosition
                if (finalPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(finalPosition, getItem(finalPosition))
                }
            }
        }

        onLongItemClickListener?.let { onLongClick ->
            holder.itemView.setOnLongClickListener {
                val finalPosition = holder.absoluteAdapterPosition
                if (finalPosition != RecyclerView.NO_POSITION) {
                    onLongClick.invoke(finalPosition, getItem(finalPosition))
                } else false
            }
        }

        bind(holder.binding, getItem(position))
        onBottomReachedListener?.invoke(isBottom(holder))
    }

    override fun getItemCount() = getData().size

    private fun isBottom(viewHolder: RecyclerView.ViewHolder) =
        viewHolder.absoluteAdapterPosition == itemCount - 1

    abstract fun inflateLayout(inflater: LayoutInflater, parent: ViewGroup): VB
    abstract fun bind(binding: VB, item: T)

    fun pushData(data: List<T>) {
        listDiffer.submitList(data)
    }

    fun isEmpty() = itemCount == 0
    fun getItem(position: Int): T = listDiffer.currentList[position]
    fun getItemPosition(item: T): Int = getData().indexOf(item)
    fun removeItem(item: T) {
        val data = getData().toMutableList()
        data.remove(item)
        pushData(data)
    }

    fun getData(): List<T> = listDiffer.currentList

    class ViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}
