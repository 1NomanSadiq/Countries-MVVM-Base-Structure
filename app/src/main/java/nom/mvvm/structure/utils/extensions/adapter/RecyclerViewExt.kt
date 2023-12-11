package nom.mvvm.structure.utils.extensions.adapter

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.attach(
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context,
        RecyclerView.VERTICAL,
        false
    ),
    adapter: BaseAdapter<T, *>,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    hasFixedSize: Boolean = false,
    snap: androidx.recyclerview.widget.SnapHelper? = null,
    onBottomReached: ((Boolean) -> Unit)? = null,
    onLongClickListener: ((Int, T) -> Boolean)? = null,
    onItemClick: ((Int, T) -> Unit)? = null,
    isNestedScrollingEnabled: Boolean = false
) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    setHasFixedSize(hasFixedSize)
    setNestedScrollingEnabled(isNestedScrollingEnabled)

    if (itemDecoration != null) {
        addItemDecoration(itemDecoration)
    }

    if (onItemClick != null) {
        adapter.onItemClickListener = { position, item ->
            onItemClick(position, item)
        }
    }

    if (onLongClickListener != null) {
        adapter.onLongItemClickListener = { position, item ->
            onLongClickListener.invoke(position, item)
        }
    }

    if (onBottomReached != null) {
        adapter.onBottomReachedListener = {
            Log.d("RecyclerView", "OnBottomReached")
            onBottomReached(it)
        }
    }

    snap?.attachToRecyclerView(this)
}

val RecyclerView.firstVisiblePosition: Int
    get() = (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: -1

val RecyclerView.lastVisiblePosition: Int
    get() = (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: -1

fun RecyclerView.firstToLastVisiblePosition(loop: (Int) -> Unit) {
    val firstVisiblePosition = firstVisiblePosition
    val lastVisiblePosition = lastVisiblePosition

    for (position in firstVisiblePosition..lastVisiblePosition) {
        loop.invoke(position)
    }
}