package com.lefu.encrypt;
  
  
public class DESCoderTest {  
  
    public void test() throws Exception {  
        String inputStr = "DES";  
        String key = DESCoder.initKey();  
        System.err.println("原文:\t" + inputStr);  
  
        System.err.println("密钥:\t" + key);  
  
        byte[] inputData = inputStr.getBytes();  
        inputData = DESCoder.encrypt(inputData, key);  
  
        byte[] outputData = DESCoder.decrypt(inputData, key);  
        String outputStr = new String(outputData);  
  
        System.err.println("解密后:\t" + outputStr);  
  
    }  
}  