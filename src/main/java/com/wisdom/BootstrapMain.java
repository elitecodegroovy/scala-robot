package com.wisdom;

import java.util.Iterator;
import java.util.Map;

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
public class BootstrapMain {

    public static void main(String[] args){
        Map<String,String> envs = System.getenv();
        Iterator it = envs.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            System.out.println("k: "+ entry.getKey() +", v:"+ entry.getValue());
        }
    }
}
