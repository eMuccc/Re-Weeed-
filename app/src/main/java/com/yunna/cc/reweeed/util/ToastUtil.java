package com.yunna.cc.reweeed.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void shortToast(String param, Context context){
        Toast.makeText(context,param,Toast.LENGTH_SHORT).show();
    }
    public static void longToast(String param, Context context){
        Toast.makeText(context,param,Toast.LENGTH_LONG).show();
    }
}
