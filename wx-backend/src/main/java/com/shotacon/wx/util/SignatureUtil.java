package com.shotacon.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class SignatureUtil {

	public static boolean signature(String token, String timestamp, String nonce, String signature) {
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		String tmpStr = null;
		try {
			tmpStr = ByteUtil.byteToStr(MessageDigest.getInstance("SHA-1").digest(StringUtils.join(arr).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
}
