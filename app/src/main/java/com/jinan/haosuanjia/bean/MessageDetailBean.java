package com.jinan.haosuanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/9.
 * 消息
 *
 */

public class MessageDetailBean implements Serializable {


    public Msginfo msginfo;
    public Aboutinfo aboutinfo;


    public class Msginfo {
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

    public class Aboutinfo {
        public int id;
        public String type_name;
        public String title;
        public String content;
        public String user_nickname;
        public String avatar;
    }
}


