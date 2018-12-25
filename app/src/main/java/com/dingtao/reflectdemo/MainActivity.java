package com.dingtao.reflectdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(id=R.id.content,name="aaa")
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        findViewById(R.id.load_dex).setOnClickListener(this);

        ViewProcess.injectViews(this);
        textView.setText("注解findviewById");
    }

    @Override
    public void onClick(View v) {
        String path = Environment.getExternalStorageDirectory()+File.separator+"aa.jar";
        useDexClassLoader(path);
    }

    private void useDexClassLoader(String path){

        File codeDir=getDir("dex", Context.MODE_PRIVATE);

        //创建类加载器，把dex加载到虚拟机中
        DexClassLoader calssLoader = new DexClassLoader(path,
                codeDir.getAbsolutePath(), null,
                this.getClass().getClassLoader());

        //利用反射调用插件包内的类的方法

        try {
            Class<?> clazz = calssLoader.loadClass("com.dingtao.dexjar.MyClass");

//            Constructor constructor = clazz.getDeclaredConstructor();//如果是私有构造方法
//            constructor.setAccessible(true);
//            constructor.newInstance();
            Object obj = clazz.newInstance();

            Method agesetMethod = clazz.getMethod("setAge",int.class);
            agesetMethod.invoke(obj,18);//方法设置值是invoke

            Method ageGetmethod = clazz.getDeclaredMethod("getAge");
            ageGetmethod.setAccessible(true);
            int age = (int) ageGetmethod.invoke(obj);

            Method helloMethod = clazz.getMethod("helloword");

            String hellovalue = (String)helloMethod.invoke(obj);

            Field nameField = clazz.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(obj,"张三");

            String nameVlaue = (String)nameField.get(obj);

            Toast.makeText(this,hellovalue+"   "+nameVlaue+":"+age,Toast.LENGTH_LONG).show();


//            Method method = clazz.getMethod("function", param);
//
//            Integer ret = (Integer)method.invoke(obj, 12,21);
//
//            Log.d("JG", "返回的调用结果为: " + ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
