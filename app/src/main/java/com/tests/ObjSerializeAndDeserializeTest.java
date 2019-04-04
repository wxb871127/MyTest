package com.tests;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;


/**
 *
 * 测试序列化和反序列化
 * @author crazyandcoder
 * @date [2015-8-5 上午11:16:14]
 */
public class ObjSerializeAndDeserializeTest {


//    public static void main(String[] args) {
//
//        //将Person对象序列化
//        SerializePerson();
//    }


    /**
     *
     * @author crazyandcoder
     * @Title: 序列化Person对象，将其存储到 E:/hello.txt文件中
     * @param
     * @return void
     * @throws
     * @date [2015-8-5 上午11:21:27]
     */
    public static void SerializePerson() {
        Person person =new Person();
        person.setAge(30);
        ObjectOutputStream outputStream = null;
        try {
            outputStream=new ObjectOutputStream(new FileOutputStream("/sdcard/hello.txt"));
            outputStream.writeObject(person);
            Log.e("xxxxxxxxxxx","序列化成功。");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Person DeserializePerson() {
        Person person = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("/sdcard/hello.txt"));
            try {
                person = (Person) inputStream.readObject();
                Log.e("xxxxxxxxxxx","执行反序列化过程成功。");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

}