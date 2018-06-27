package com.jinan.haosuanjia.utils;

/**
 * 网络请求封装类
 * @author zgc
 */
public class UrlUtils {
	//获取服务器地址url
	public static String getBaseUrl() {
		return HMApplication.KP_BASE_URL+"/";
	}
	//获取服务器域名地址url
	public static String getBaseUrlYu() {
		return HMApplication.KP_BASE_URL_YU;
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

	//获取劳务供需劳务输出列表
	public static String getSupplyList() {
		return getBaseUrl() + "labor/get_supply_list";
	}
	//获取劳务供需劳务需求列表
	public static String getDemandList() {
		return getBaseUrl() + "labor/get_demand_list";
	}
	//版本更新接口
	public static String getAndroidVersiont() {
		return getBaseUrl() + "common/get_android_version";
	}
	//获取劳务供需劳务需求留言
	public static String getDemandComment() {
		return getBaseUrl() + "labor/add_demand_comment";
	}
	//添加买卖需求留言
	public static String getBuyComment() {
		return getBaseUrl() + "business/add_buy_comments";
	}
	//添加买卖供应留言
	public static String getSellComment() {
		return getBaseUrl() + "business/add_sell_comments";
	}
	//获取劳务供需劳务输出留言
	public static String getSupplyComment() {
		return getBaseUrl() + "labor/add_supply_comment";
	}
	//获取劳务供需劳务需求报价
	public static String getDemandOffer() {
		return getBaseUrl() + "labor/add_demand_offer";
	}
	//添加买卖需求报价
	public static String getBuyOffer() {
		return getBaseUrl() + "business/add_buy_offer";
	}
	//添加买卖供应报价
	public static String getSellOffer() {
		return getBaseUrl() + "business/add_sell_offer";
	}
	//获取劳务供需劳务输出报价
	public static String getSupplyOffer() {
		return getBaseUrl() + "labor/add_supply_offer";
	}
	//添加论坛评论
	public static String getAddComments() {
		return getBaseUrl() + "circle/add_circle_comments";
	}
	//添加论坛收藏
	public static String getAddCollection() {
		return getBaseUrl() + "circle/add_circle_collection";
	}
	//获取买卖需求购买
	public static String getBuyList() {
		return getBaseUrl() + "business/get_buy_list";
	}
	//获取买卖需求出售
	public static String getSellList() {
		return getBaseUrl() + "business/get_sell_list";
	}

	//根据不同排序获取不同的公司列表
	public static String getCommpanyList() {
		return getBaseUrl() + "company/get_company_list";
	}
	//新闻页重点企业列表
	public static String getNewsCommpanyList() {
		return getBaseUrl() + "news/get_news_list";
	}
	//获取所有经纪人
	public static String getAgentList() {
		return getBaseUrl() + "common/get_all_agent";
	}
	//添加论坛朋友圈
	public static String getAddCircle() {
		return getBaseUrl() + "circle/add_circle";
	}
	//添加劳务需求
	public static String AddDemand() {
		return getBaseUrl() + "labor/add_demand";
	}
	//关于我们
	public static String getAboutUs() {
		return getBaseUrl() + "common/get_about";
	}
	//添加劳务输出
	public static String AddSupply() {
		return getBaseUrl() + "labor/add_supply";
	}
	//获取论坛朋友圈列表
	public static String getCircleList() {
		return getBaseUrl() + "circle/get_circle_list";
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
