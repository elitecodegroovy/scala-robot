package com.wisdom.common;

import akka.dispatch.Mapper;
import akka.japi.Function;
import akka.japi.Function2;
import akka.japi.Option;
import org.testng.Assert;

/**
 * @author john.grails@gmail.com
 * @since 2015/12/13..
 */
public class CallingFunc {

    public  void callFunc(){
        Function<Integer, String> f = new Function<Integer, String>() {
            public String apply(Integer i) {
                return i.toString();
            }
        };
        try {
            String s = f.apply(9);
            Assert.assertEquals("9", s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Function2<Integer, Integer, String> f2 =
                new Function2<Integer, Integer, String>() {
                    public String apply(Integer i, Integer j) {
                        return new Integer(i + j).toString();
                    }
                };
        String s = null;
        try {
            s = f2.apply(9, 100);
            Assert.assertEquals("109", s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        callOption();
        callMapper();
    }

    public void callOption(){
        // When the Option has something
        Option<String> opt = new Option.Some<String>("Something");
        Assert. assertTrue(opt.isDefined());
        Assert.assertFalse(opt.isEmpty());
        Assert.assertEquals("Something", opt.get());
        Assert.assertTrue(opt.iterator().hasNext());
        Assert.assertEquals("Something", opt.iterator().next());
// When the Option has nothing
        Option<String> none = Option.none();
// Or equivalently: Option.option(null);
        Assert.assertFalse(none.isDefined());
        Assert.assertTrue(none.isEmpty());
        try {
            none.get();
            Assert.fail();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(none.iterator().hasNext());
    }

    /**
     * mapper function
     */
    public void callMapper(){
        Mapper<Integer, String> f = new Mapper<Integer, String>() {
            public String apply(Integer i) {
                return i.toString();
            }
        };
        Assert.assertEquals("9", f.apply(9));
    }
}
