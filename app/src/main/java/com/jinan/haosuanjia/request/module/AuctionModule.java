package com.jinan.haosuanjia.request.module;

import android.content.Context;

import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.VolleyUtilKupai;
import com.jinan.haosuanjia.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by darcy  on 2016/03/15
 * Function: 拍卖品模块
 * Modify by darcy on 2016/03/15
 * Modify Reason:
 */
public class AuctionModule extends BaseModule {
    private static AuctionModule singleton;

    private AuctionModule() {

    }

    public static AuctionModule getInstance() {
        if (singleton == null)
            synchronized (AuctionModule.class) {
                singleton = new AuctionModule();
            }
        return singleton;
    }

    /**
     * 获取验证码
     */
    public void getPhoneCode(Context context, String PhoneNumber, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getYanZhengMa();

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("PhoneNumber", PhoneNumber);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 登录
     */
    public void getLogin(Context context, String PhoneNumber, String PassWord, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getMemberLogin();

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("PhoneNumber", PhoneNumber);
        mapParams.put("PassWord", PassWord);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 注册jpush
     */
    public void RegisteJpush(Context context,String registration_id, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getRegisteJpush();

        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("PhoneNumber", PhoneNumber);
        mapParams.put("registration_id", registration_id);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 注册
     */
    public void getRegister(Context context,String PhoneNumber, String PassWord,String PhoneCode,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getRegist();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("PhoneNumber", PhoneNumber);
        mapParams.put("PassWord", PassWord);
        mapParams.put("PhoneCode", PhoneCode);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取劳务供需劳务输出列表
     */
    public void getSupplyList(Context context,int page, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSupplyList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取劳务供需劳务需求列表
     */
    public void getDemandList(Context context,int page, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getDemandList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务供需劳务需求留言
     */
    public void getAddDemandComment(Context context,String demand_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getDemandComment();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("demand_id", demand_id);
        mapParams.put("user_id", user_id);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加买卖需求留言
     */
    public void getAddBuyComment(Context context,String buy_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getBuyComment();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("buy_id", buy_id);
        mapParams.put("user_id", user_id);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加买卖供应留言
     */
    public void getAddSellComment(Context context,String sell_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSellComment();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("sell_id", sell_id);
        mapParams.put("user_id", user_id);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务供需劳务输出留言
     */
    public void getAddSupplyComment(Context context,String supply_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSupplyComment();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("supply_id", supply_id);
        mapParams.put("user_id", user_id);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务供需劳务需求报价
     */
    public void getAddDemandOffer(Context context,String demand_id,String user_id,String price,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getDemandOffer();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("demand_id", demand_id);
        mapParams.put("user_id", user_id);
        mapParams.put("price", price);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加买卖需求报价
     */
    public void getAddBuyOffer(Context context,String buy_id,String user_id,String price,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getBuyOffer();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("buy_id", buy_id);
        mapParams.put("user_id", user_id);
        mapParams.put("price", price);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加买卖供应报价
     */
    public void getAddSellOffer(Context context,String sell_id,String user_id,String price,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSellOffer();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("sell_id", sell_id);
        mapParams.put("user_id", user_id);
        mapParams.put("price", price);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务供需劳务输出报价
     */
    public void getAddSupplyOffer(Context context,String supply_id,String user_id,String price,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSupplyOffer();

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("supply_id", supply_id);
        mapParams.put("user_id", user_id);
        mapParams.put("price", price);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加论坛评论
     */
    public void getAddComments(Context context,String circle_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAddComments();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("circle_id", circle_id);
        mapParams.put("user_id", user_id);
//        mapParams.put("status", status);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加论坛收藏
     */
    public void getAddCollection(Context context,String circle_id,String user_id,String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAddCollection();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("circle_id", circle_id);
        mapParams.put("user_id", user_id);
//        mapParams.put("status", status);
//        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取买卖需求购买列表
     */
    public void getBuyList(Context context,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getBuyList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取买卖需求出售列表
     */
    public void getSellList(Context context,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getSellList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }

    /**
     * 根据不同排序获取不同的公司列表
     */
    public void getCommpanyList(Context context,String class_id,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getCommpanyList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取冷库租赁列表
     */
    public void getColdStorageList(Context context,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getColdStorageList();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取公司企业重点企业
     */
    public void getNewsCommpanyList(Context context,String class_id,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getNewsCommpanyList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取资讯列表
     */
    public void getNewsInformationList(Context context,String class_id,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getNewsInformationList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取资讯详情
     */
    public void getNewsInformationDetail(Context context,String class_id,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getNewsInformationDetail();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("id", class_id);
//        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取资讯顶部banner
     */
    public void getBannerList(Context context,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getBannerList();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("class_id", class_id);
//        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取所有经纪人
     */
    public void getAgentList(Context context,String class_id,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAgentList();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取语音播报列表
     */
    public void getAudioBroadcastList(Context context,String zone,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAudioBroadcastList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("zone", zone);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取我的消息
     */
    public void getMessageList(Context context,String user_id,int page,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getMessageList();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("id", user_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加论坛朋友圈
     */
    public void getAddCircle(Context context,String user_id,String title, String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAddCircle();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("user_id", user_id);
        mapParams.put("title", title);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加意见反馈
     */
    public void getAddFeedback(Context context,String title, String content,BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAddFeedback();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("title", title);
        mapParams.put("content", content);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务需求
     */
    public void getAddDemand(Context context,String unitName,String address,String phone,String workContent,String startDate,
                             String endDate,String workDays,String workers,String price,String amount,String status,String user_id,String user_nickname, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.AddDemand ();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("unitName", unitName);
        mapParams.put("address", address);
        mapParams.put("phone", phone);
        mapParams.put("workContent", workContent);
        mapParams.put("startDate", startDate);
        mapParams.put("endDate", endDate);
        mapParams.put("workDays", workDays);
        mapParams.put("workers", workers);
        mapParams.put("price", price);
        mapParams.put("amount", amount);
//        mapParams.put("status", status);
        mapParams.put("user_id", user_id);
//        mapParams.put("user_nickname", user_nickname);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加购蒜需求
     */
    public void getAddBuy(Context context ,String crop ,String address ,String phone ,String spec ,String wantPrice,
                             String requirement ,String amount ,String user_id, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.AddBuy();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("unitName", unitName);
        mapParams.put("crop", crop);
        mapParams.put("amount", amount);
        mapParams.put("spec", spec);
        mapParams.put("wantPrice", wantPrice);
        mapParams.put("address", address);
        mapParams.put("requirement", requirement);
        mapParams.put("phone", phone);
        mapParams.put("user_id", user_id);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 行情页根据省份获取平均价格列表
     */
    public void getQuotationByZone(Context context ,String zone,String cropid,String month, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getQuotationByZone();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("unitName", unitName);
        mapParams.put("zone", zone);
        mapParams.put("cropid", cropid);
        mapParams.put("month", month);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 根据所属ID获取地区列表
     */
    public void getAreaParent(Context context ,String parentid, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAreaParent();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("unitName", unitName);
        mapParams.put("parentid", parentid);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 行情页获取乡镇级品种行情
     */
    public void getQuotationByTwon(Context context ,String zone ,String twon,String cropid,String month, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getQuotationByTwon();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("unitName", unitName);
        mapParams.put("zone", zone);
        mapParams.put("twon", twon);
        mapParams.put("cropid", cropid);
        mapParams.put("month", month);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加出售大蒜
     */
    public void getAddSell(Context context ,String crop ,String address ,String phone ,String spec ,String wantPrice,
                          String sellDesc ,String amount ,String user_id, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.AddSell();
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("crop", crop);
        mapParams.put("amount", amount);
        mapParams.put("spec", spec);
        mapParams.put("wantPrice", wantPrice);
        mapParams.put("address", address);
        mapParams.put("sellDesc", sellDesc);
        mapParams.put("phone", phone);
        mapParams.put("user_id", user_id);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务输出
     */
    public void getAddSupply(Context context,String user_nickname,String supplyNum,String workType,String startDate,
                             String endDate,String address,String phone,String flag,String createTime,String user_id, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.AddSupply();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("user_nickname", user_nickname);
        mapParams.put("supplyNum", supplyNum);
        mapParams.put("workType", workType);
        mapParams.put("startDate", startDate);
        mapParams.put("endDate", endDate);
        mapParams.put("address", address);
        mapParams.put("phone", phone);
//        mapParams.put("flag", flag);
//        mapParams.put("createTime", createTime);
        mapParams.put("user_id", user_id);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 添加劳务输出
     *
     */
    public void getAddColdLease(Context context,String cold_storage_cate,String price,String amount,String surplus,
                             String daylength,String address,String user_id, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.AddColdLease();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("user_nickname", user_nickname);
        mapParams.put("cold_storage_cate", cold_storage_cate);
        mapParams.put("price", price);
        mapParams.put("amount", amount);
        mapParams.put("surplus", surplus);
        mapParams.put("daylength", daylength);
        mapParams.put("address", address);
//        mapParams.put("flag", flag);
//        mapParams.put("createTime", createTime);
        mapParams.put("user_id", user_id);
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
    /**
     * 获取论坛朋友圈列表
     */
    public void getCircleList(Context context, int page, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getCircleList();
        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }

    /**
     * 关于我们
     */
    public void getAboutUs(Context context, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getAboutUs ();
        Map<String, String> mapParams = new HashMap<>();
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
}
