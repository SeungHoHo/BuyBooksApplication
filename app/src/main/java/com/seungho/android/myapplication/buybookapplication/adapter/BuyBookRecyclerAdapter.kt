package com.seungho.android.myapplication.buybookapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seungho.android.myapplication.buybookapplication.databinding.ViewholderBuyBinding
import com.seungho.android.myapplication.buybookapplication.data.entity.RoomBook
import com.seungho.android.myapplication.buybookapplication.data.db.RoomHelper

class BuyBookRecyclerAdapter: RecyclerView.Adapter<BuyBookRecyclerAdapter.Holder>() {

    var roomHelper: RoomHelper? = null
    var listData = mutableListOf<RoomBook>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ViewholderBuyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val roomBook = listData[position]
        holder.setRoomBook(roomBook)
    }

    override fun getItemCount(): Int {
       return listData.size
    }

    inner class Holder(private val binding: ViewholderBuyBinding) : RecyclerView.ViewHolder(binding.root) {
        var mRoomBook: RoomBook? = null

        @SuppressLint("SetTextI18n")
        fun setRoomBook(roomBook: RoomBook) {
            val title = roomBook.title
            val afterTitle1 = title?.replace("<b>", "")
            val afterTitle2 = afterTitle1?.replace("</b>", "")
            binding.buyBookTitleText.text = afterTitle2
            binding.buyBookAuthorText.text = "${roomBook.author} 지음 | ${roomBook.publisher}"
            if (roomBook.discount == "" || roomBook.discount.isEmpty()) {
                binding.buyBookPriceText.text = "${roomBook.price}원"
            } else {
                binding.buyBookPriceText.text = "${roomBook.discount}원"
            }
        }


    }


}