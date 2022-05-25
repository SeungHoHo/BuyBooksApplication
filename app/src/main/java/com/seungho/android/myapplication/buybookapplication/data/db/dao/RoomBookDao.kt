package com.seungho.android.myapplication.buybookapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.seungho.android.myapplication.buybookapplication.data.entity.RoomBook

@Dao
interface RoomBookDao {

    @Query("select * from room_book")
    suspend fun getBookList(): List<RoomBook>

    @Insert(onConflict = REPLACE)
    suspend fun insert(book: RoomBook)
}