package com.jinan.haosuanjia.utils.okgo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hu on 2017/6/21.
 */

public class UploadBitmapModel implements Serializable {
    public String imageId;
    public String srcName;
    public List<Files> files;

    public class Files {

        public String srcName;
        public String path;
        public int width;
        public int height;
        public int error;
        public boolean success;
        public String type;
        public String size;
    }
}
