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
	public static String getBaseUrlFile() {
		return HMApplication.KP_BASE_URL_FILE;
	}
	//注册接口
	public static String getRegist() {
		return getBaseUrl() + "user/register";
	}
	//找回密码接口
	public static String getfindpassword() {
		return getBaseUrl() + "doctor/findPassword.action";
	}
	//登录接口
	public static String getMemberLogin() {
		return getBaseUrl() + "user/login";
	}
	//注册jpush
	public static String getRegisteJpush() {
		return getBaseUrl() + "user/registe_jpush";
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
	public static String getAddircleCollection() {
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
	//获取冷库租赁列表
	public static String getColdStorageList() {
		return getBaseUrl() + "company/get_cold_lease";
	}
	//新闻页重点企业列表
	public static String getNewsCommpanyList() {
		return getBaseUrl() + "company/get_company_list";
	}
	//获取信息咨询列表
	public static String getNewsInformationList() {
		return getBaseUrl() + "news/get_news_list";
	}
	//获取信息咨询详情
	public static String getNewsInformationDetail() {
		return getBaseUrl() + "news/get_news_by_id";
	}
	//添加新闻评论
	public static String getAddNewsComments() {
		return getBaseUrl() + "news/add_news_comments";
	}
	//获取信息咨询banner列表
	public static String getBannerList() {
		return getBaseUrl() + "news/get_banner_list";
	}
	//获取所有经纪人
	public static String getAgentList() {
		return getBaseUrl() + "common/get_all_agent";
	}
	//获取语音播报
	public static String getAudioBroadcastList() {
		return getBaseUrl() + "news/get_music_news_list";
	}
	//获取我的消息列表
	public static String getMessageList() {
		return getBaseUrl() + "message/get_message_list";
	}
	//获取金币配置列表
	public static String getCoinConfig() {
		return getBaseUrl() + "common/get_coin_config";
	}
	//更新头像
	public static String getUpdateAvatart() {
		return getBaseUrl() + "user/update_avatar";
	}
	//更新用户昵称
	public static String getUpdateUserName() {
		return getBaseUrl() + "user/update_user_nickname";
	}
	//添加论坛朋友圈
	public static String getAddCircle() {
		return getBaseUrl() + "circle/add_circle";
	}
	//添加意见反馈
	public static String getAddFeedback() {
		return getBaseUrl() + "common/add_feedback";
	}
	//添加劳务需求
	public static String AddDemand() {
		return getBaseUrl() + "labor/add_demand";
	}
	//添加购蒜需求
	public static String AddBuy() {
		return getBaseUrl() + "business/add_buy";
	}
	//行情根据省份获取平均价格列表
	public static String getQuotationByZone() {
		return getBaseUrl() + "quotation/get_quotation_by_zone";
	}
	//行情获取乡镇级品种行情
	public static String getQuotationByTwon() {
		return getBaseUrl() + "quotation/get_quotation_by_twon";
	}
	//根据所属ID获取地区列表
	public static String getAreaParent() {
		return getBaseUrl() + "common/get_area_parent";
	}
	//添加出售大蒜
	public static String AddSell() {
		return getBaseUrl() + "business/add_sell";
	}
	//关于我们
	public static String getAboutUs() {
		return getBaseUrl() + "common/get_about";
	}
	//添加订单接口
	public static String getCreateOrder() {
		return getBaseUrl() + "alipayment/create_new_order";
	}
	//获取支付状态
	public static String getOrderResult() {
		return getBaseUrl() + "alipayment/get_order_result";
	}
	//获取金币信息
	public static String getUserCoin() {
		return getBaseUrl() + "user/get_user_coin";
	}
	//添加劳务输出
	public static String AddSupply() {
		return getBaseUrl() + "labor/add_supply";
	}
	//添加冷库租赁信息
	public static String AddColdLease() {
		return getBaseUrl() + "company/add_cold_lease";
	}
	//上传图片接口
	public static String AddUpload() {
		return getBaseUrl() + "user/upload_avatar";
	}
	//获取论坛朋友圈列表
	public static String getCircleList() {
		return getBaseUrl() + "circle/get_circle_list";
	}

}
