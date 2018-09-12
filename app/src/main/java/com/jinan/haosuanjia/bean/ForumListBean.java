package com.jinan.haosuanjia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 * 论坛
 *
 */

public class ForumListBean implements Serializable {

    public int id;
    public int user_id;
    public String title;
    public String content;
    public String createtime;
    public int collection;
    public int comments;
    public int status;
    public String avatar;
    public int is_collection;
    public String user_nickname;
    public List<CommentsList> comments_list;

    public class CommentsList {
        public int id;
        public int user_id;
        public String content;
        public String createtime;
        public int circle_id;
        public int status;
        public String avatar;
        public String user_nickname;
    }
}


