package com.wisdom;

import com.wisdom.common.Thinking;

import java.util.Iterator;
import java.util.Map;

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
public class BootstrapMain {

    public static void main(String[] args){
//        printSysEnv();
        new Thinking().callThinkingInScala();
    }

    public static void printSysEnv(){
        Map<String,String> envs = System.getenv();
        Iterator it = envs.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            System.out.println("k: "+ entry.getKey() +", v:"+ entry.getValue());
        }
    }
}
