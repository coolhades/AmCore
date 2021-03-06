package com.hades.corelib;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hades on 2016/1/1.
 * 抽象工厂类
 */

public class AmFactory {
    Map<String, String> holdermap;
    Map<String, String> viewmap;

    public static AmFactory getInstance() {
        return SingleInstance.instance;
    }

    private AmFactory() {
        holdermap = new HashMap<>();
        viewmap = new HashMap<>();
    }

    private static class SingleInstance {
        private static AmFactory instance = new AmFactory();
    }


    public AmFactory regist(String code, String conponentName) {
        holdermap.put(code, conponentName);
        return this;
    }

    public AmFactory registView(String code, String name) {
        viewmap.put(code, name);
        return this;
    }


    //创建Fragment
    @Deprecated
    public Object create(String code, int maptype) {
        //通过code去查conponentName，看是否注册了
        Object o = null;
        String conponentName = "";
        try {
            switch (maptype) {
                case MAPTYPE.HOLDERMAP:
                    conponentName = holdermap.get(code);
                    break;
                case MAPTYPE.VIEWMAP:
                    conponentName = viewmap.get(code);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            o = Class.forName(conponentName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return o;
    }



    /**
     * 创建时间 2016/11/4
     * auther Hades
     * 描述   根据布局创建对应的ViewHolder
     **/
    public Constructor createHolder(String code, int maptype) {
        Constructor<?> constructor = null;
        if (!holdermap.containsKey(code)){
            return null;
        }
        //获取Class
        Class<?> cls = null;
        try {
            Log.d("AmFactory", "ClassName = "+holdermap.get(code));
            cls = Class.forName(holdermap.get(code));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (cls != null) {
                constructor = cls.getConstructor(View.class);
            }else throw new NullPointerException("反射失败！请检查组件是否正确注册？");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }


    public Map<String, String> getHoldermap() {
        return holdermap;
    }

    public Map<String, String> getViewmap() {
        return viewmap;
    }
}
