package com.wisdom.common;

import com.ai.common.Accessor;
import com.ai.common.Person;
import com.ai.common.ThinkingInScala;
import com.ai.common.ThinkingInScala$;

/**
 * @author john.grails@gmail.com
 * @since 2015/11/28..
 */
public class Thinking {

    public void callThinkingInScala(){
        new ThinkingInScala().think();
        ThinkingInScala.study();  //// [2] (static)
        ThinkingInScala$.MODULE$.think();  //hidden [3] (static)

        callProperties();
        callInitParameters();
    }

    /**
     * access the common scala object's variables
     */
    public void callProperties(){
        Accessor.size_$eq(10);
        System.out.println(Accessor.size());

        Accessor.size_$eq(20);
        System.out.println(Accessor.size());

        Accessor.name_$eq("Calling scala object's variables");
        System.out.println(Accessor.name());

        System.out.println(" apply: "+ Accessor.greet().apply("John Liu"));
        Accessor.greetWords_$eq(", nice to meet you!");
        System.out.println(" apply: "+ Accessor.greet().apply("John Liu"));

        scala.Function1<String, String> _greeting = Accessor.greet();
        System.out.println(_greeting.apply("Mrt Java"));
    }

    public void callInitParameters(){
        Person bob =
                new Person("Bob", // [1]
                        28,
                        "John");
        System.out.println(bob);
        bob.setEmail(bob.setEmail$default$1()); // [2]
        System.out.println(
                new Person("Bob", // [3]
                        22,
                        "Lausanne"));
    }


}
