package com.gk.cbl.util;

public class MathUtil {
    public static String getRandFloat(){
        Integer fu = (int)(Math.random()*5+100);
        Integer shi =  (int)(Math.random()*1000);
        String abc = fu+"."+shi;
        return abc;
    }

    public static String getRa(){
        Integer fu = (int)(Math.random()*5+20);
        Integer shi =  (int)(Math.random()*1000);
        String abc = fu+"."+shi;
        return abc;
    }
}
