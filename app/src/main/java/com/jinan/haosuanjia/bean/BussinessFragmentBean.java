package com.jinan.haosuanjia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 *
 */

public class BussinessFragmentBean implements Serializable {

   public int type;
   public User user;
   public Moment moment;
   public String activityName;
   public String activityId;
   public ActivityClass activityClass;
   public List<Comments> comments;
   public List<Topics> topics;
   public List<String> topicsname;

    public class User {
        public String uid;
        public String nick;
        public String city;
        public String createTime;
        public String updateTime;
        public String background;
        public String birthday;
        public String cityId;
        public String cityName;
        public String intro;
        public String memberId;
        public String phone;
        public String petName;
        public String provinceName;
        public String photo;
        public int supported;
        public int provinceId;
        public int gender;
        public int status;
        public int type;
    }

    public class Moment {
        public int accessCnt;
        public int commentsCnt;
        public String content;
        public String createTime;
        public String latitude;
        public int likeCnt;
        public int liked;
        public String location;
        public String longitude;
        public String momentId;
        public String pics;
        public int show;
        public int type;
        public String uid;
        public String updateTime;
        public String video;
        public String yn;
        public String activityId;
        public String activityName;
    }

    public class Comments {
        public String commentId;
        public String content;
        public String createTime;
        public String itemId;
        public int itemType;
        public int parentId;
        public String petName;
        public String toUid;
        public String toUserNick;
        public String toUserPhoto;
        public String uid;
        public String updateTime;
        public String userNick;
        public String userPhoto;
        public int visible;

    }
    public class Topics {

        public int topicId;
        public String name;

    }

    public class ActivityClass {
        public String classId;
        public String name;
        public String pic;
        public String hotIcon;
        public String textIcon;
        public String cornerIcon;
    }
}


