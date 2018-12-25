package com.dingtao.reflectdemo;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewProcess {

    /**
     * 绑定Activity
     */
    public static void injectViews(Activity activity) {
        // 获取activity的Class
        Class<? extends Activity> object = activity.getClass();
        // 通过Class获取activity的所有属性
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            // 获取字段的注解，如果没有ViewInject注解，则返回null
            BindView viewInject = field.getAnnotation(BindView.class);
            if (viewInject != null) {
                // 获取属性的注解的参数，这就是控件的id
                int viewId = viewInject.id();
                try {
                    // 获取类中的findViewById方法，参数为int
                    Method method = object.getMethod("findViewById", int.class);
                    // 执行该方法，返回一个Object类型的View实例
                    Object resView = method.invoke(activity, viewId);

                    field.setAccessible(true);
                    // 把属性的值设置为该View的实例
                    field.set(activity, resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
