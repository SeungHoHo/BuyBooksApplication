package com.seungho.android.myapplication.buybookapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seungho.android.myapplication.buybookapplication.data.db.dao.RoomBookDao
import com.seungho.android.myapplication.buybookapplication.data.entity.RoomBook

@Database(entities = arrayOf(RoomBook::class), version = 1, exportSchema = false )
abstract class RoomHelper: RoomDatabase() {
    abstract fun roomBookDao(): RoomBookDao
}