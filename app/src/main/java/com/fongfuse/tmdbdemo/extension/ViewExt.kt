package com.fongfuse.tmdbdemo.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Context.toast(message: String){
    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
}