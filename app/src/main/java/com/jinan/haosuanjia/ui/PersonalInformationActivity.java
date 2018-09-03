package com.jinan.haosuanjia.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.InformationConsultationBean;
import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.dialog.CustomDialogEditText;
import com.jinan.haosuanjia.dialog.DialogDefaultLoading;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.FileUtils;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.JinChaoUtils;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.PermissionUtil;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.utils.UpLoadInfoUtils;
import com.jinan.haosuanjia.utils.UrlUtils;
import com.jinan.haosuanjia.utils.okgo.JsonCallback;
import com.jinan.haosuanjia.utils.okgo.LzyResponse;
import com.jinan.haosuanjia.utils.okgo.OKhttpUtil;
import com.jinan.haosuanjia.utils.okgo.UploadBitmapModel;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static com.jinan.haosuanjia.utils.okgo.OKhttpUtil.*;

/**
 * 个人资料
 * create by gc on 2018/08/22
 */
public class PersonalInformationActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private ImageView iv_user_photo;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private TextView tv_names;
    private TextView tv_details;

    private String cold_storage_cate;
    private String price;
    private String amount;
    private String daylength;
    private String surplus;
    private String address;
    private DialogDefaultLoading defaultLoading;
    private final int REQUEST_CHANGE_HEAD_PHOTO = 1;
    private final int REQUEST_TAKE_PHOTO = 2;
    private final int REQUEST_CHOOSE_PHOTO = 3;
    private final int REQUEST_CHANGE_NICKNAME = 4;
    private final int REQUEST_CHANGE_SEX = 5;
    private final int REQUEST_CHANGE_BIRTHDAY = 6;
    private final int REQUEST_CHANGE_BREED = 7;
    private final int REQUEST_CLIP_OVER = 8;
    private final int ADD_FINISH = 9;

    //原图像 路径
    private static String imgPathOri;
    //裁剪图像 路径
    private static String imgPathCrop;
    //原图像 URI
    private Uri imgUriOri;
    //裁剪图像 URI
    private Uri imgUriCrop;
//    private String job_start_date;
//    private String job_end_date;
//    private String flag="0";//状态 0：正常，1：作废
//    private String createTime="2018-06-20";

    private String userNickname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initUI();
        initdata();
    }

    private void initdata() {
//        initDataPost(true);
    }

    private void initUI() {

        defaultLoading = new DialogDefaultLoading(this);
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        tv_names = (TextView) findViewById(R.id.tv_names);
        tv_details = (TextView) findViewById(R.id.tv_details);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_names.setOnClickListener(this);
        tv_title_head.setText("个人资料");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("修改");
        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
        tv_names.setText(SPUtil.get(ConstantString.USERNICKNAME));
        tv_details.setText("手机号："+ SPUtil.get(ConstantString.PHONENUM));
        BitmapUtil.loadImageUrl(iv_user_photo, R.drawable.ic_launcher_background, HMApplication.KP_BASE_URL_YU+SPUtil.get(ConstantString.AVATAR));
//

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_names:
                new CustomDialogEditText.Builder(context, new CustomDialogEditText.Builder.PriorityListener() {
                    @Override
                    public void setActivityText(String content) {
                        Upadate_USerName(content);
                        userNickname=content;
                    }
                })
                        .setMessage("修改昵称")
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
//                                        LogX.d("setPagePath",
//                                                "" +
//                                                        "报价");
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();
                break;
            case R.id.tv_right_head:
                Intent intent=new Intent(context,SelectSexPicPopWindowActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent,REQUEST_CHANGE_HEAD_PHOTO);
//                overridePendingTransition(R.anim.timepicker_anim_enter_bottom,
//                        R.anim.timepicker_anim_exit_bottom);
                break;
            default:
                break;
        }
    }
    private void Upadate_USerName(final String user_nickname) {
        AuctionModule.getInstance().getUpdateUserName(context, user_nickname, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        try{
                            tv_names.setText(user_nickname);
                            SPUtil.set(ConstantString.USERNICKNAME,user_nickname);
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
                }
            }

            @Override
            public void onGotError(String code, String error) {
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CHANGE_HEAD_PHOTO) {
                if (data != null) {
                    changeHeadPhoto(data.getIntExtra("selectItem", 1));
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {// 拍照返回
                // 存储卡可用
                dojudgeStorage(imgUriOri);
            } else if (requestCode == REQUEST_CHOOSE_PHOTO) {// 调用系统裁剪
                dojudgeStorage(data.getData());
            } else if (requestCode == REQUEST_CLIP_OVER) {// 裁剪完成上传图片
                JinChaoUtils.scanFileAsync(context, new File(imgPathCrop));
//                ImageUtils.loadImageWithError(imgPathCrop, R.mipmap.icon_user_default_head, iv_head_photo);
//                toUploadPhotos();
//                uploadPic();
//                try {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 4;
//                    Bitmap bitmap = BitmapFactory.decodeFile(headFile.toString(),
//                            options);
//                    if (bitmap != null)// 保存图片
//                    {
//                        iv_head.setImageBitmap(bitmap);
                        uploderPic(type_pic);
//                    }
//
//                    if(bitmap != null && bitmap.isRecycled()){
//                        bitmap.recycle();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                revokeUriPermission(imgUriCrop, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }else if (requestCode == ADD_FINISH) {// 添加积分完成
                finish();
            }
        }
    }
    int type_pic = 1;
    int type_bg = 2;

    /**
     * 上传图片
     */
    private void uploderPic(final int type) {
        File file = new File(imgPathCrop);
        defaultLoading.show();

        new UpLoadInfoUtils(new UpLoadInfoUtils.UpLoadPicCallBack() {
            @Override
            public void onSuccess(int statusCode, String content) {
                upLoadPicSuccess(content, type);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                ShowToastUtil.Short("图片上传失败");
                defaultLoading.dismiss();
            }
        }, file, 1).startUploadPic();

//        doUpLoadPic(file, type);
//        uploadPic();
    }
/* */   private void upLoadPicSuccess(String reslut, int type) {
        try {
            JSONObject jsonObject = new JSONObject(reslut);

            if (jsonObject.getInt("status")!=1) {
                ShowToastUtil.Short(ConstantString.TEXT_EMPTY
                        + jsonObject.getString("msg"));
                return;
            }
            int nError = jsonObject.getInt("errorCode");
            if (nError != 0) {
                ShowToastUtil.Short(ConstantString.TEXT_EMPTY
                        + jsonObject.getString("msg"));
                return;
            }


            String imgUrl=jsonObject.getJSONObject("data").getJSONArray("files").getJSONObject(0).getString("path");
            if(!TextUtils.isEmpty(imgUrl)){
                SPUtil.set(ConstantString.AVATAR,imgUrl);
                Upadate_Head(imgUrl);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(imgPathCrop,
                        options);
                if (bitmap != null)// 保存图片
                {
                    iv_user_photo.setImageBitmap(bitmap);
                }
            }else{
                ShowToastUtil.Short("更新头像失败，请稍后重试!");
            }
//            if (type == 1) {
//                updateUserInfo(
//                        UPDATE_USERINFO_TYPE_HEADPHOTO,
//                        jsonObject.getJSONObject("data").getString(
//                                "imageId"));
//            } else if (type == 2) {
//                uploderPBg(jsonObject.getJSONObject("data").getString("imageId"));
//            }

        } catch (Exception e) {
            e.printStackTrace();
            ShowToastUtil.Short("未知异常!");
        }

        if (defaultLoading != null && defaultLoading.isShowing() && !isFinishing())
            defaultLoading.dismiss();
    }

    private void Upadate_Head(String avatar) {
                String user_id=SPUtil.get(ConstantString.USERID);
        AuctionModule.getInstance().getUpdateAvatart(context,user_id, avatar, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        try{

                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
                }
            }

            @Override
            public void onGotError(String code, String error) {
            }
        });
    }
    public void doUpLoadPic(File file, final int type){
        // TODO: 2017/6/21 由OKhttp的图片上传替换掉AsyncHttpClient
        OKhttpUtil.upLoadFile( file, new JsonCallback<LzyResponse<UploadBitmapModel>>() {
            @Override
            public void onSuccess(Response<LzyResponse<UploadBitmapModel>> response) {
                UploadBitmapModel result = response.body().data;
//                if (type == 1) {
//                    updateUserInfo(UPDATE_USERINFO_TYPE_HEADPHOTO, result.imageId);
//                } else if (type == 2) {
//                    uploderPBg(result.imageId);
//                }

                if (defaultLoading != null && defaultLoading.isShowing() && !isFinishing())
                    defaultLoading.dismiss();
            }

            @Override
            public void onError(Response<LzyResponse<UploadBitmapModel>> response) {
                super.onError(response);
                ShowToastUtil.Short("图片上传失败");
                if (defaultLoading != null && defaultLoading.isShowing() && !isFinishing())
                    defaultLoading.dismiss();
            }
        });
    }
   /* private void toUploadPhotos() {
        String uid = SPUtil.get(ConstantString.USERID);
        if (!uid.equals("")) {
            NetWorkModule.getInstance().getEssStsParams(context, new BaseHandlerJsonObject() {
                @Override
                public void onGotJson(JSONObject result) {
                    essStsBean = ParseJson.parseConvertResultObject(result, EssStsBean.class);

                    if (customProgressDialog == null)
                        customProgressDialog = CustomProgressDialog.show(
                                context, "1/1", "上传头像中，请耐心等待~",
                                1, true, false,
                                new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                    }
                                });
                    tryUploadTime = 0;
                    uploadPic();
                }

                @Override
                public void onGotError(String code, String error) {

                }
            });
        } else {
            ARouterUtils.toPage("/login/login");
        }
    }*/
    private void uploadPic() {
        File file = new File(imgPathCrop);
        if (!JinChaoUtils.hasNetwork(context)) {
            Toast.makeText(context, "请连接网络之后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        if (file.exists() && file.length() > 0) {
            final String url = UrlUtils.AddUpload();
//
//            String authorization = EssAuthorizationUtil.getESSTokenForUpload(
//                    EssAuthorizationUtil.getCurrentGMTTime(),
//                    essStsBean.accessKeyId,
//                    essStsBean.secretAccessKey,
//                    "multipart/form-data",
//                    "",
////                    MD5Util.getFileMD5(file),
//                    "");
//            LogX.e("Authorization", authorization);

            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getPath(), fileBody)
                    .build();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
//                    .addHeader("Date", EssAuthorizationUtil.getCurrentGMTTime())
//                    .addHeader("x-ess-security-token", essStsBean.accessKeyId)
//                    .addHeader("Authorization", authorization)
//                .addHeader("Content-MD5", MD5Util.getFileMD5(file))
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Content-Length", String.valueOf(file.length()))
                    .build();

            final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = httpBuilder
                    //设置超时
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
//            final DialogDefaultLoading dialogUtils = new DialogDefaultLoading(context);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, final okhttp3.Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            UploadResultBean resultBean = null;
                            try {
                                defaultLoading.dismiss();
                            Log.e(HMApplication.TAG, "uploadMultiFile() response=" + response.body().string());
//                                resultBean = ParseJson.parseConvertString2Object(response.body().string(), UploadResultBean.class);
                                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.body().toString());
//                                JsonObject jsonObject = new JsonObject(response.body().toString());
                                //                                if (resultBean.key != null) {
//                                    try {
//                                        BitmapFactory.Options options = new BitmapFactory.Options();
//                                        options.inSampleSize = 4;
//                                        Bitmap bitmap = BitmapFactory.decodeFile(imgPathCrop,
//                                                options);
//                                        if (bitmap != null)// 保存图片
//                                        {
//                                            iv_head_photo.setImageBitmap(bitmap);
//                                        }
//                                        Toast.makeText(context, "头像上传成功~", Toast.LENGTH_SHORT).show();
//                                        if (bitmap != null && bitmap.isRecycled()) {
//                                            bitmap.recycle();
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
////                                    headPhotoKey = resultBean.key;
////                                    if (petId != null && !petId.equals("")) {
////                                        Map<String, String> mapParams = new HashMap<>();
////                                        mapParams.put("photo", headPhotoKey);
////                                        updatePetInfos(mapParams);
////                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (defaultLoading != null) {
                                    defaultLoading.dismiss();
                                }
                            }
                        }
                    });

                }

                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            customProgressDialog.dismiss();
                            Log.e(HMApplication.TAG, "uploadMultiFile() e=" + e);
                            Toast.makeText(context, "图片上传失败", Toast.LENGTH_SHORT)
                                    .show();
                            if (defaultLoading != null) {
                                defaultLoading.dismiss();
                            }
                        }
                    });
                }
            });
        } else {
//            if (tryUploadTime > 20) {
//                customProgressDialog.dismiss();
//                Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
//            } else {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        tryUploadTime++;
//                        uploadPic();
//                    }
//                }, 1000);
//            }
        }
    }

    /**
     * 修改头像
     */
    private void changeHeadPhoto(int selectItem) {
        if (selectItem == 1) { // 相机拍照
            if (!PermissionUtil.checkCameraPermission(this)) return;
            if (!PermissionUtil.checkStoragePermission(this)) return;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            File oriPhotoFile = null;
            try {
                oriPhotoFile = createOriImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (oriPhotoFile != null) {
                imgPathOri = oriPhotoFile.getAbsolutePath();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    imgUriOri = Uri.fromFile(oriPhotoFile);
                } else {
                    imgUriOri = FileProvider.getUriForFile(this, getPackageName() + ".provider", oriPhotoFile);
                }
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } else {
                ShowToastUtil.Short("创建本地图片失败");
            }

//            if (!PermissionUtil.checkCameraPermission(this)) return;
//            if (!PermissionUtil.checkStoragePermission(this)) return;
//            if (!headFile.exists()) {
//                headFile = new File(FileUtils.getFilesDirSystem(), "acyHead.JPG");
//            }
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
//            Uri u = Uri.fromFile(headFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
//            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        } else if (selectItem == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_CHOOSE_PHOTO);
            } else {
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.setType("image/*");
                startActivityForResult(albumIntent, REQUEST_CHOOSE_PHOTO);
            }
        }
    }
    private void dojudgeStorage(Uri uri) {
        if (uri == null) return;
        // 存储卡可用
        if (JinChaoUtils.isHasSdcard() && PermissionUtil.checkStoragePermission(this)) {
            // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
            startPhotoZoom(uri);
        }
    }

    /**
     * 裁剪图片
     */
    private void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {// 4.4以上
//            String url = UuUtils.getPath(context, uri);
//            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
//        } else { // 4.4以下
//            intent.setDataAndType(uri, "image/*");
//        }
//        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
//        intent.putExtra("output", Uri.fromFile(headFile));
//        intent.putExtra("outputFormat", "JPEG");// 返回格式
//        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
//        intent.putExtra("aspectY", 1);// x:y=1:1
//        startActivityForResult(intent, REQUEST_CLIP_OVER);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.setDataAndType(uri, "image/*");
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {// 4.4以上
            String url = JinChaoUtils.getPath(context, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else { // 4.4以下
            intent.setDataAndType(uri, "image/*");
        }
        File cropPhotoFile = null;
        try {
            cropPhotoFile = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cropPhotoFile != null) {
            //7.0 安全机制下不允许保存裁剪后的图片
            //所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                imgUriCrop = Uri.fromFile(cropPhotoFile);
//            } else {
//                imgUriCrop = FileProvider.getUriForFile(this, getPackageName() + ".provider", cropPhotoFile);
//            }
            imgUriCrop = Uri.fromFile(cropPhotoFile);
            imgPathCrop = cropPhotoFile.getAbsolutePath();

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriCrop);
            intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
            intent.putExtra("outputFormat", "JPEG");// 返回格式
            intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
            intent.putExtra("aspectY", 1);// x:y=1:1
            startActivityForResult(intent, REQUEST_CLIP_OVER);
        } else {
            ShowToastUtil.Short("创建本地图片失败");
        }
    }
    /**
     * 创建原图像保存的文件
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "Head_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File pictureDirOri = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        return File.createTempFile(
                imgNameOri,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirOri       /* directory */
        );
    }

}
