package com.yaogd.nativ;

public class LoadNative {
    
    static {
        System.loadLibrary("stat") ;
    }
    
    public static native String get() ;
}
