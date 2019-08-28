package com.nhat910.videocalldemo.utils

import android.support.annotation.IntDef
import android.support.annotation.StringRes
import android.widget.Toast
import com.nhat910.videocalldemo.MyApplication

@IntDef(Toast.LENGTH_LONG, Toast.LENGTH_SHORT)
private annotation class ToastLength

fun shortToast(@StringRes text: Int) {
    shortToast(MyApplication.getApplication().getString(text))
}

fun shortToast(text: String) {
    show(text, Toast.LENGTH_SHORT)
}

fun longToast(@StringRes text: Int) {
    longToast(MyApplication.getApplication().getString(text))
}

fun longToast(text: String) {
    show(text, Toast.LENGTH_LONG)
}

private fun makeToast(text: String, @ToastLength length: Int): Toast {
    return Toast.makeText(MyApplication.getApplication(), text, length)
}

private fun show(text: String, @ToastLength length: Int) {
    makeToast(text, length).show()
}