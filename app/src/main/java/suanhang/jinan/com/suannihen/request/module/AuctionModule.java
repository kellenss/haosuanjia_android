package suanhang.jinan.com.suannihen.request.module;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import suanhang.jinan.com.suannihen.request.BaseHandlerJsonObject;
import suanhang.jinan.com.suannihen.request.VolleyUtilKupai;
import suanhang.jinan.com.suannihen.utils.UrlUtils;

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
     * 获取论坛朋友圈列表
     */
    public void getCircleList(Context context, int page, BaseHandlerJsonObject responseHandler) {
        String url = UrlUtils.getCircleList();

        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("class_id", class_id);
        mapParams.put("page", page+"");
        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
    }
//    /**
//     * 获取首页列表
//     */
//    public void getAllpatient(Context context, BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getAllpatient();
//
//        Map<String, String> mapParams = new HashMap<>();
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 查找
//     */
//    public void getsearch(Context context,String nameORbedcode, BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getsearch();
//
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("nameORbedcode", nameORbedcode);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//
//    /**
//     * 获取日程列表
//     */
//    public void getScheduleList(Context context,String patient_id, BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getScheduleList();
//
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("patient_id_", patient_id);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 添加患者接口
//     */
//    public void getAddpatient(Context context,String bed_code,String username,String doctor_id ,BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getAddpatient();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("bed_code", bed_code);
//        mapParams.put("name", username);
//        mapParams.put("doctor_id", doctor_id);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 删除患者接口（批量）
//     */
//    public void getDeletepatient(Context context,String ids ,BaseHandlerJsonObject responseHandler) {
//        ids=ids.replace(" ","").replace(" ","");
//        String url = UrlUtils.getDeletepatient();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("ids_", ids);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//
//    /**
//     * 添加日程接口
//     */
//    public void getAddschedule(Context context,String content,String doctor_id ,String patient_id,String img_ids,BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getAddschedule();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("content", content);
//        mapParams.put("doctor_id", doctor_id);
//        mapParams.put("patient_id", patient_id);
//        mapParams.put("img_ids", img_ids);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 日程详情接口
//     */
//    public void getScheduleDetail(Context context,String id_ ,BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getScheduleDetail();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("id_", id_);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 日程状态切换接口
//     */
//    public void getUpdateIstatusSchedule(Context context,String id_ ,String istatus ,BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getUpdateIstatusSchedule();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("id_", id_);
//        mapParams.put("istatus", istatus);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
//    /**
//     * 日程编辑更新接口
//     */
//    public void getUpdateSchedule(Context context,String id_ ,String content ,String img_ids ,String is_empty,BaseHandlerJsonObject responseHandler) {
//        String url = UrlUtils.getUpdateSchedule();
//        Map<String, String> mapParams = new HashMap<>();
//        mapParams.put("id_", id_);
//        mapParams.put("content", content);
//        mapParams.put("img_ids", img_ids);
//        mapParams.put("is_empty", is_empty);
//        VolleyUtilKupai.sendPostMethod(url, mapParams, responseHandler, true, context);
//    }
}
