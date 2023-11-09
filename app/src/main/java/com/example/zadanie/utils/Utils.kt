package eu.mcomputing.mobv.mobvzadanie

import androidx.recyclerview.widget.DiffUtil
import com.example.zadanie.adapters.MyItem

class ItemDiffCallback(
    private val oldList: List<MyItem>,
    private val newList: List<MyItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}