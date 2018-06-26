package com.jinan.haosuanjia.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断一些格式的工具类
 * @author zhang
 *
 */

/**
 * 手机号码:
 * 13[0-9], 14[5,7, 9], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0-9], 18[0-9]
 * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
 * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
 * 电信号段: 133,149,153,170,173,177,180,181,189
 */
public class FormatTools {
	//判断手机号
	public static boolean IsPhoneNum(String mobiles) {
		if (mobiles.trim().length() == 11) {
			Pattern p = Pattern
//					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");
//			.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[06-8]|8[0-9])\\d{8}$");
			.compile("^1(3[0-9]|4[579]|5[0-35-9]|7[0-9]|8[0-9])\\d{8}$");
			Matcher m = p.matcher(mobiles);
			Log.d("IsPhoneNum", m.matches() + "");
			return m.matches();
		}
		return false;

	}
	//判断字符串是数字
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
//		   System.out.println(str.charAt(i));
		   if (!Character.isDigit(str.charAt(i))){
			   return false;
		   }
		}
		return true;
	}
}
