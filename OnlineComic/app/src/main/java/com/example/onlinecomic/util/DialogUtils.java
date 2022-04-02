package com.example.onlinecomic.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.NoCopySpan;

import androidx.appcompat.app.AlertDialog;

import com.example.onlinecomic.callback.DialogClickListener;

public class DialogUtils {

    public static void showDialog(Context context, String title, String message, DialogClickListener l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(message).setCancelable(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                l.onPositive();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
