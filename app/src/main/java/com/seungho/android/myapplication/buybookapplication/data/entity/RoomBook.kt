package com.seungho.android.myapplication.buybookapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_book")
class RoomBook {

    @PrimaryKey
    @ColumnInfo
    var isbn: String = ""

    @ColumnInfo
    var title: String = ""

    @ColumnInfo
    var author: String = ""

    @ColumnInfo
    var publisher: String = ""

    @ColumnInfo
    var discount: String = ""

    @ColumnInfo
    var price: String = ""

    constructor(isbn: String, title: String, author: String, publisher: String, discount: String, price: String) {
        this.isbn = isbn
        this.title = title
        this.author = author
        this.publisher = publisher
        this.discount = discount
        this.price = price
    }
}