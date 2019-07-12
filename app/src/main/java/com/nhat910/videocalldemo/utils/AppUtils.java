package com.nhat910.videocalldemo.utils;

import android.content.Context;
import android.text.TextUtils;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.others.MessageDialog;

public class AppUtils {
    public static boolean validateLogin(Context context, String title, String userName, String passWord) {
        MessageDialog messageDialog = new MessageDialog(context, title, context.getString(R.string.login_fail_content), null);
        if (TextUtils.isEmpty(userName)) {
            messageDialog.show();
            return false;
        }
        if (TextUtils.isEmpty(passWord)) {
            messageDialog.show();
            return false;
        }
        if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(passWord)) {
            messageDialog.show();
            return false;
        }
        return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord);
    }
}
