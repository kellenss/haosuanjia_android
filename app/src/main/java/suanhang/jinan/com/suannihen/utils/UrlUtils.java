package suanhang.jinan.com.suannihen.utils;

/**
 * 网络请求封装类
 * @author zgc
 */
public class UrlUtils {
	//获取服务器地址url
	public static String getBaseUrl() {
		return HMApplication.KP_BASE_URL+"/";
	}

	//注册接口
	public static String getRegist() {
		return getBaseUrl() + "user/register";
	}
//	//注册接口
//	public static String getRegist() {
////		return getBaseUrl() + "registerUser.php";
//		return  "http://cmf.haosuanjia.com/android/user/register";
//	}
	//找回密码接口
	public static String getfindpassword() {
		return getBaseUrl() + "doctor/findPassword.action";
	}

	//登录接口
	public static String getMemberLogin() {
		return getBaseUrl() + "user/login";
	}

	//获取验证码
	public static String getYanZhengMa() {
		return getBaseUrl() + "getPhonecode.php";
	}

	//获取劳务供需劳务需求列表
	public static String getSupplyList() {
		return getBaseUrl() + "labor/get_supply_list";
	}
	//获取劳务供需劳务输出列表
	public static String getDemandList() {
		return getBaseUrl() + "labor/get_demand_list";
	}
	//获取买卖需求购买
	public static String getBuyList() {
		return getBaseUrl() + "business/get_buy_list";
	}
	//获取买卖需求出售
	public static String getSellList() {
		return getBaseUrl() + "business/get_sell_list";
	}

	/*//添加患者信息接口
	public static String getAddpatient() {
		return getBaseUrl() + "patient/add.action";
	}
	//添加日程接口
	public static String getAddschedule() {
		return getBaseUrl() + "schedule/add.action";
	}
	//日程详情接口
	public static String getScheduleDetail() {
		return getBaseUrl() + "schedule/look.action";
	}
	//更新日程状态接口
	public static String getUpdateIstatusSchedule() {
		return getBaseUrl() + "schedule/updateIstatus.action";
	}
	//更新日程内容接口
	public static String getUpdateSchedule() {
		return getBaseUrl() + "schedule/update.action";
	}
	//删除患者信息接口
	public static String getDeletepatient() {
		return getBaseUrl() + "patient/falseDel.action";
	}

	//患者列表接口
	public static String getAllpatient() {
		return getBaseUrl() + "patient/all.action";
	}

	//患者列表接口
	public static String getsearch() {
		return getBaseUrl() + "patient/findBynameorcode.action";
	}
	//日程列表接口
	public static String getScheduleList() {
		return getBaseUrl() + "schedule/all.action";
	}
	//添加患者日程接口（上传图片）
	public static String getUpLoadPhoto() {
		return getBaseUrl() + "common/uploadImg.action";
	}*/
}
