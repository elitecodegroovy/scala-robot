package com.wisdom;

import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.Recover;
import akka.dispatch.OnSuccess;
import akka.dispatch.OnFailure;
import akka.dispatch.OnComplete;
import akka.util.Timeout;
import static akka.dispatch.Futures.future;
// Things we need from Scala
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author john.grails@gmail.com
 * @since 2015/12/14..
 */
public class JavaFutureSpecTest {
    // The Future needs somewhere to execute, which exists as the
    // ExecutionContext (we can use a Dispatcher as well, but we're going
// to do this a little lower down).
    static ExecutorService es;
    static ExecutionContext ec;
    // We're going to need a timeout later, so we'll use it from here , 2 s.
    static Timeout timeout = new Timeout(2000);
    // Sets up the ExecutionContext
    @BeforeClass
    public static void setup() {
        es = Executors.newFixedThreadPool(2);
        ec = ExecutionContexts.fromExecutorService(es);
    }
    // Shuts down our thread pool
    @AfterClass
    public static void teardown() {
        es.shutdown();
    }

    @Test(priority = 1)
    public void testCallable() throws Exception {
        Future<String> f = future(new Callable<String>() {
            public String call() {
                return "It's easy to use.";
            }
        }, ec);
        Assert.assertEquals("It's easy to use.", (String) Await.result(f, timeout.duration()));
    }

    @Test(priority = 2)
    public void testMap() throws Exception {
        Future<Integer> f = future(new Callable<String>() {
            public String call() {
                return "Fibonacci";
            }
// And then map that string into an Integer
        }, ec).map(new Mapper<String, Integer>() {
            public Integer apply(String s) {
                return s.length();
            }
        }, ec);
        Assert.assertEquals(new Integer(9), Await.result(f, timeout.duration()));
    }

    @Test(priority = 3)
    public void testFlatMap() throws Exception {
        // Ultimately we're going to get back a Future<Integer>
        Future<Integer> f = future(new Callable<String>(){
            // But we start with a String
            public String call() {
                return "Fibonacci";
            }
// flatMap it to a Future<Integer> (i.e. not an Integer as before)
        }, ec).flatMap(new Mapper<String, Future<Integer>>() {
            public Future<Integer> apply(final String s){
// Construct a new Future to an eventual Integer
                return future(new Callable<Integer>() {
                    public Integer call() {
                        return s.length();
                    }
                }, ec);
            }
        }, ec);
        Assert.assertEquals(new Integer(9), Await.result(f, timeout.duration()));
    }
    @Test(priority = 4)
    public void testSequencing() throws Exception {
        ArrayList<Future<Integer>> v = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 200; i++) {
            final int num = i;
            v.add(future(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return new Integer(num);
                }
            }, ec));
        }
// Compose that list of Future Integers into a Future of a
// List of Integers
        Future<Iterable<Integer>> futureListOfInts = Futures.sequence(v, ec);
        // Map over the results and sum them up
        Future<Long> f = futureListOfInts.map(
                new Mapper<Iterable<Integer>, Long>() {
                    public Long apply(Iterable<Integer> ints) {
                        long sum = 0;
                        for (Integer i : ints) sum += i;
                        return sum;
                    }
                }, ec);
        Assert.assertEquals(new Long(19900),  Await.result(f, timeout.duration()));
    }

    @Test(priority = 5)
    public void testCallbacks(){
        Future<Integer> f = future(new Callable<Integer>() {
            public Integer call() {
                return new Integer(42);
            }
        }, ec);
// Add the onSuccess callback
        f.onSuccess(new OnSuccess<Integer>() {
            public void onSuccess(Integer result){
                System.out.println("Awesome! I got a number successfully! " + result);
            }
        }, ec);
// Add the onFailure callback
        f.onFailure(new OnFailure() {
            public void onFailure(Throwable failure){
                System.out.println("Aw, poo! It didn't work: " + failure);
            }
        }, ec);
// Add the onComplete callback
        f.onComplete(new OnComplete<Integer>() {
            public void onComplete(Throwable failure, Integer result) {
                if (failure != null)
                    System.out.println("Aw, poo! It didn't work: " + failure);
                else
                    System.out.println("Awesome! I completely got a number : " + result);
            }
        }, ec);
    }

    @Test(priority = 6)
    public void testRecovering() throws Exception {
        Future<Integer> f = future(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("--------exception happened.------");
                throw new Exception("You ain't gettin' no Integer from me.");
            }
        }, ec).recover(new Recover<Integer>() {
            //In other words, you recover with a Recover<Future<Integer>>instead of just a Recover<Integer>.
            public Integer recover(Throwable t) throws Throwable {
                return new Integer(0);
            }
        }, ec);
        Assert.assertEquals(new Integer(0), Await.result(f, timeout.duration()));
    }
}
