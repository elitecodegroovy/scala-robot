package com.wisdom.common;

import com.ai.common.Print;
import com.ai.common.Print$class;

/**
 *  A Scala trait with no method implementation is just an interface
 *  at the bytecode level and can thus be used in Java code like any
 *  other interface.
 *  However, if a trait has method implementations (eg. method
 *  println(s: String) in the example below), then Java classes
 *  implementing that trait must provide concrete methods for
 *  abstract methods with no code and forwarder methods for
 *  abstract methods containing code.
 * @author john.grails@gmail.com
 * @since 2015/11/29..
 */
public class PrintLog implements Print {
    @Override
    public void print(String s) {
        System.out.println(s);
    }

    @Override
    public void println() {
        System.out.println("------------------------------------------");
    }

    @Override
    public void println(String s) {
        Print$class.println(this, s);
    }

    public static void main(String[] args){
        new PrintLog().println("Hello, Java & Scala");
    }
}
