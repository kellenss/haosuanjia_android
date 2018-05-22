package suanhang.jinan.com.suannihen.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * sharedpreferences工具类
 * @author zgc
 *
 */
public class SPUtil {
    private static final String  SUANNIHEN= "suannihen";

    public static SharedPreferences getSP() {
		return HMApplication.application.getSharedPreferences(SUANNIHEN, 0);
	}
    public static String get(String what) {
        return getSP().getString(what, "");
    }

	public static String get(Context context, String what) {
		return getSP().getString(what, "");
	}

	public static void set(Context context, String what, String value) {
		getSP().edit().putString(what, value).apply();
	}
    
	public static void set(String what, String value) {
		getSP().edit().putString(what, value).apply();
	}

	public static int getInt(Context context, String what) {
		return getSP().getInt(what, 0);
	}

    public static int getInt(String what) {
        return getSP().getInt(what, 0);
    }

	public static void setInt(Context context, String what, int value) {
		getSP().edit().putInt(what, value).apply();
	}
	public static void setInt(String what, int value) {
		getSP().edit().putInt(what, value).apply();
	}
	
	public static String getPush(Context context, String what) {
		return getSP().getString(what, "1");
	}
	
	public static void setBoolean(Context context, String what, boolean value) {
		getSP().edit().putBoolean(what, value).apply();
	}

	public static void setBoolean( String what, boolean value) {
		getSP().edit().putBoolean(what, value).apply();
	}
	
	public static boolean getBoolean(Context context, String what) {
		return getSP().getBoolean(what, false);
	}
	
	public static void removeSP(Context context, String what) {
		getSP().edit().remove(what).apply();
	}
	public static String getfeedCount(Context context, String what) {
		return getSP().getString(what, "0");
	}

	public static void setfeedCount(Context context, String what, String value) {
		getSP().edit().putString(what, value).apply();
	}

    ///////////////////////////////////////////////////////////////////////////
    // 只能保存32个状态值的SharedPreferences，参数是ConstantString.SPUTIL_GUIDE_STATE
    ///////////////////////////////////////////////////////////////////////////
    public static final int STATE_VOICE =                           1;//拍品详情页音效控制
    public static final int STATE_GUIDE_AUCTIONDETAIL =             1<<1;//拍品详情页引导图
    public static final int STATE_GUIDE_BIDVERSION_DETAIL =         1<<2;//拍品详情页提示：不显示绝杀区，自由出价区用户即可触发绝杀
    public static final int STATE_GUIDE_BIDVERSION_BIDHALF =        1<<3;//出价器提示：不显示绝杀区，自由出价区用户即可触发绝杀
    /**
     * 以二进制存储状态，每一位分别代表一个不同的状态
     * state = false设置为0
     * state = true设置为1
     */
	@SuppressLint("CommitPrefEdits")
    public static void setGuideState(int value, boolean sta) {
        SharedPreferences sharedPreferences = getSP();
        int state = sharedPreferences.getInt(ConstantString.SPUTIL_GUIDE_STATE, 0);
        if(sta)
            sharedPreferences.edit().putInt(ConstantString.SPUTIL_GUIDE_STATE, state | value).commit();
        else
            sharedPreferences.edit().putInt(ConstantString.SPUTIL_GUIDE_STATE, state & ~value).commit();
	}

    public static boolean getGuideState(int oldState) {
        int guide = getSP().getInt(ConstantString.SPUTIL_GUIDE_STATE, 0);
        return (guide & oldState)  == oldState;
    }
}
