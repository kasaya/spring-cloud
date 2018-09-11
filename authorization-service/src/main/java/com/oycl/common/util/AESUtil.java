package com.oycl.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 * 
 */
public class AESUtil {

	/**
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	private static String sKey = "a";
	private String ivParameter = "b";
	private static AESUtil instance = null;

	private AESUtil() {

	}

	public static AESUtil getInstance() {
		if (instance == null) {
			sKey = "c";
			instance = new AESUtil();
		}
		return instance;
	}

	public String encryptAES(String text, String key) {
		try {

			if(key.length() < 32){
				key = StringUtils.rightPad(key,32,"0");
			}

			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

			Security.addProvider(new BouncyCastleProvider());
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");


			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			// 真正开始加密操作
			byte[] encryptedArray = cipher.doFinal(text.getBytes("UTF-8"));
			String encodedStr = Base64.encodeBase64String(encryptedArray);

			return encodedStr;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public String decryptCBC(String sSrc) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(decodeSlash(sSrc));// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 加密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public String encryptCBC(String sSrc) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return encodeSlash(new BASE64Encoder().encode(encrypted));// 此处使用BASE64做转码。
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 加密后替换斜杠
	 * @param sSrc
	 * @return
	 */
	private String encodeSlash(String sSrc) {
		if (sSrc != null) {
			return sSrc.replaceAll("/", "_").replaceAll("\\+", "-").replaceAll("\\r", "").replaceAll("\\n", "");
		}
		return null;
	}
	
	/**
	 * 解密前还原斜杠
	 * @param sSrc
	 * @return
	 */
	private String decodeSlash(String sSrc) {
		if (sSrc != null) {
			return sSrc.replaceAll("_", "/").replaceAll("\\-", "+");
		}
		return null;
	}

}
