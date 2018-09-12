package com.jinan.haosuanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/9.
 * 消息
 *
 */

public class MessageListBean implements Serializable {

    public int id;
    public int from;
    public int to;
    public String title;
    public String content;
    public int type;
    public int aboutid;
    public String createtime;
    public int issend;
    public int is_read;
    public String from_user;
    public String type_name;
    public String to_user;

}


