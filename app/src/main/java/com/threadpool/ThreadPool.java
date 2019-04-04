package com.threadpool;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
//https://www.cnblogs.com/wihainan/p/4765862.html
public class ThreadPool {
    public class ThreadPools implements Callable<Integer> {
        private int num;

        public ThreadPools(int num) {
            this.num = num;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 1; i <= num; i++) {
                sum += i;
                Thread.sleep(100);
            }
            return sum;
        }
    }

    public  void test(){
        try {
            ExecutorService pool = Executors.newFixedThreadPool(2);
            Future<Integer> future1 = pool.submit(new ThreadPools(10));
            Integer s1 = future1.get();
            Future<Integer> future2 = pool.submit(new ThreadPools(100));
            Integer s2 = future2.get();
            Log.e("xxxxxxx", "s1 = " + s1 + "; s2 = " + s2);
            pool.shutdown();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

