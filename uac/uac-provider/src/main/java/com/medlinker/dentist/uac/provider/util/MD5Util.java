package com.medlinker.dentist.uac.provider.util;

import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 32位小写
	 * 
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static String MD5(byte[] btInput) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * md5生成二进制
	 * 
	 * @param s
	 * @return
	 */
	public final static byte[] MD5ToBytes(String s) {
		try {
			String md5 = MD5Util.MD5(s);
			if (md5 != null) {
				return HexUtil.hexStringToBytes(md5);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 加密后从指定位数取出long
	 * @param s
	 * @param start
	 * @param end
	 * @return
	 */
	public final static Long getLongFromMd5(String s, int start, int end) {
		if (start >= end) {
			throw new RuntimeException("start必须小于end");
		}
		if (s == null) {
			return 0l;
		}
		byte[] md5 = MD5Util.MD5ToBytes(s);
		long result = 0l;
		int length = end - start;
		for (int i = 0; i < length; i++) {
			result += ((Long.valueOf(md5[i + start]) & 0xff) << (i * 8));
		}
		return result;
	}
}
