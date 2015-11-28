package com.wisdom.common;

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
    }
}
