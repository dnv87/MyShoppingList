package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //как создать View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        //определяем загружаемый layout
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view =
            LayoutInflater.from(parent.context).inflate(
                layout,
                parent,
                false
            )
        return ShopItemViewHolder(view)
    }

    //как отобразить
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }
        viewHolder.tvName.text = "${shopItem.name} $status"
        viewHolder.tvCount.text = shopItem.count.toString()
        viewHolder.view.setOnLongClickListener {
            true
        }
    }


    override fun getItemCount(): Int {
        return shopList.size
    }

    //определяем viewType для элементов списка
    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }


    // класс который хранит View
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)!!
        val tvCount = view.findViewById<TextView>(R.id.tv_count)!!
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2

           const val MAX_POOL_SIZE = 10
    }
}