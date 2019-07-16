package com.nhat910.videocalldemo.utils;


import android.content.Context;
import android.support.annotation.Nullable;

import com.nhat910.videocalldemo.interfaces.ConfirmListener;
import com.nhat910.videocalldemo.others.MessageDialog;

public class AppUtils {
    public static void showDialogMessage(Context context, String title,String content, @Nullable ConfirmListener listener) {
        MessageDialog messageDialog = new MessageDialog(context, title, content, listener);
        messageDialog.show();
    }
}
