package com.hades.corelib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Hades on 2016/11/3.
 * 根据布局创建view
 */

public class LayoutInflaterFactory {

    LayoutInflater mLayoutInfalter;
    Map<String, String> map;

    public static LayoutInflaterFactory getInstance(){
        return SignalInstance.instance;
    }
    private LayoutInflaterFactory() {
        map = new HashMap<>();
    }

    private static class SignalInstance{
        private static LayoutInflaterFactory instance = new LayoutInflaterFactory();
    }

    /**
    * 创建时间 2016/11/16
    * auther Hades
    * 描述  layoutType key
     *      layoutid  value
    **/
    public LayoutInflaterFactory registLayoutId(String layoutType, String layoutid){
        map.put(layoutType, layoutid);
        return this;
    }

    //加载布局
    public View inflaterView(String layouttype, ViewGroup parent){
        if (!map.containsKey(layouttype)) return null;
        return mLayoutInfalter.from(parent.getContext()).inflate(Integer.valueOf(map.get(layouttype) ), parent, false);
    }
}
