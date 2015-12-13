package com.wisdom;

import akka.japi.Function;
import akka.japi.Function2;
import com.wisdom.common.CallingFunc;
import com.wisdom.common.Thinking;
import org.testng.Assert;

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

        CallingFunc func = new CallingFunc();
        func.callFunc();
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
