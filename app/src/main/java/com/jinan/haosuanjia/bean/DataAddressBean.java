package com.jinan.haosuanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/12.
 * 省县镇数据结构
 *
 */

public class DataAddressBean implements Serializable {

    public int areaid;
    public String areaname;
    public int parentid;
    public String arrparentid;
    public int child;
    public String arrchildid;
    public int listorder;

}


