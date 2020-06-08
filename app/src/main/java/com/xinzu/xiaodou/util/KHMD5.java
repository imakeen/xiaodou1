package com.xinzu.xiaodou.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class KHMD5 {
	
	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	// 主输出
	public static String getMD5String(String temp) {
		String resultString = null;
		try {
			resultString = new String(temp);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byte32ToString(md.digest(temp.getBytes()));
			// resultString = byte64ToString(md.digest(temp.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	// MD5返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// MD5返回形式只为数字
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// MD5转换字节数组为16进制字串，32位加密
	private static String byte32ToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}



	public static void main(String[] args) {
		String md5String = getMD5String("xzcxpt112233");
		System.out.println(md5String);
	}
}
