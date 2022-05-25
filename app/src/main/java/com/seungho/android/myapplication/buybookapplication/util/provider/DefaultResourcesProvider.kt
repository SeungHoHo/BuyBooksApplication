package com.seungho.android.myapplication.buybookapplication.util.provider

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat

class DefaultResourcesProvider(
    private val context: Context
): ResourcesProvider {

    override fun getString(resId: Int): String = context.getString(resId)

    override fun getString(resId: Int, vararg formArgs: Any): String = context.getString(resId, *formArgs)

    override fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)

    override fun getColorStateList(resId: Int): ColorStateList = context.getColorStateList(resId)

}