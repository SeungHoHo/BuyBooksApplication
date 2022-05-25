package com.seungho.android.myapplication.buybookapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seungho.android.myapplication.buybookapplication.databinding.ViewholderBookBinding
import com.seungho.android.myapplication.buybookapplication.screen.home.detail.BookDetailActivity
import com.seungho.android.myapplication.buybookapplication.data.response.Item

class BookRecyclerAdapter(private val listData: List<Item>, private val context: Context): RecyclerView.Adapter<BookRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRecyclerAdapter.Holder {
        val binding = ViewholderBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: BookRecyclerAdapter.Holder, position: Int) {
        holder.setBooks(listData[position], context)
    }

    override fun getItemCount(): Int = listData.size

    inner class Holder(private val binding: ViewholderBookBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setBooks(items: Item, context: Context) {

            Glide.with(context)
                .load(items.image)
                .into(binding.bookImageView)

            val title = items.title
            val afterTitle1 = title?.replace("<b>", "")
            val afterTitle2 = afterTitle1?.replace("</b>", "")
            binding.bookTitleText.text = afterTitle2
            binding.bookAuthorText.text = items.author
            binding.bookPublisherText.text = "${items.publisher} | ${items.pubdate}"
            binding.bookPriceText.text = "${items.price}원"

            binding.viewHolder.setOnClickListener {
                val intent = Intent(context, BookDetailActivity::class.java)
                intent.putExtra("Naver", items)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }


}