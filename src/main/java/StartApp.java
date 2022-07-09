package main.java;
import main.java.ui.MainForm;

import java.util.concurrent.*;


/**
 * Created by Ziaee on 12/6/2021.
 */
public class StartApp {
    public static void main(String[] args) {
        MainForm.getInstance().setVisible(true);


//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            Runnable worker = new Runnable() {
//                private String command = "12313";
//
//
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
//                    processCommand();
//                    int j=0;
//                    for (int k=0; k<100; k++)
//                    for (int i=0; i<1000000000; i++) {
//                        j+=i;
//                    }
//                    System.out.println(j);
//
//                    System.out.println(Thread.currentThread().getName()+" End.");
//                }
//
//                private void processCommand() {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public String toString(){
//                    return this.command;
//                }            };
//            executor.execute(worker);
//        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }
//        System.out.println("Finished all threads");
//
//





//        final int a1=1000;
//        final int a2=3000;
//        final int a3=2000;
//        final int a4=4000;
//
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(a1);
//                    System.out.println("t1");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(a2);
//                    System.out.println("t2");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(a3);
//                    System.out.println("t3");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Thread t4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(a4);
//                    System.out.println("t4");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//
//        try {
//            System.out.println("tttt");
//
//            t1.start();
//            t1.join();
//
//            t2.start();
//            t2.join();
//
//            t3.start();
//            t3.join();
//
//            t4.start();
//            t4.join();
//
//
//
//
//            System.out.println("tttt");
//
//        } catch (Throwable t) {
//            System.out.println("err");
//
//        }


    }



}