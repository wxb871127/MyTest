package com.threadpool;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class ThreadPriority {

    public class ThreadP extends Thread
    {
        public ThreadP(String name){
            super(name);
        }

        @Override
        public void run () {
            for (int x = 0; x < 1000; x++)
                Log.e(getName() , x+"");
            super.run();
        }
    }

    public void test(){
        Thread thread1 = new ThreadP("林浩1");
        Thread thread2 = new ThreadP("林浩2");
        Thread thread3 = new ThreadP("林浩3");

        thread1.setPriority(10);
        thread3.setPriority(1);

        thread1.start();
        thread2.start();
        thread3.start();
        List list = Collections.synchronizedList(new ArrayList<>());
    }

}
