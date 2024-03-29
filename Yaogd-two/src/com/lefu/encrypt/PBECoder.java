package com.lefu.encrypt;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
/**
 * Password-based encryption（基于密码加密）
 * @author yaoguangdong
 * 2014-2-17
 */
public class PBECoder {
	public static final String ALGORITHM = "PBEWITHMD5andDES";
	//迭代次数
	public static final int ITERATION_COUNT = 100;
	/**
	 * 盐初始化<br>
	 * 盐长度必须为8字节
	 * @return byte[] 盐
	 */
	public static byte[] initSalt(){
		//实例化安全随机数
		SecureRandom random = new SecureRandom();
		//生产盐
		return random.generateSeed(8);
	}
	/**
	 * 转换密钥
	 * @param password 密码
	 * @return key 密钥
	 * @throws Exception
	 */
	private static Key toKey(String password)throws Exception{
		//密钥材料转换
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		//实例化
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}
	/**
	 * 加密
	 * @param data 数据
	 * @param password 密码
	 * @param salt 盐
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{
		Key key = toKey(password);
		//实例化PBE参数材料
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key,parameterSpec);
		return cipher.doFinal(data);
	}
	public static byte[] decrypt(byte[] data, String password,byte[] salt) throws Exception{
		Key key = toKey(password);
		//实例化PBE参数材料
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key,parameterSpec);
		return cipher.doFinal(data);
	}
}
