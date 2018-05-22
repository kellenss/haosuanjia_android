package suanhang.jinan.com.suannihen.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.TipsList;

import static suanhang.jinan.com.suannihen.R.mipmap.icon_home_banner;

/**
 * Created by hu on 2017/08/11.
 *
 */
public class TipsViewPagerAdapter extends CommonPagerAdapter<TipsList> {
    Context context;
    private int layoutRes;

    public TipsViewPagerAdapter(Context context, @Nullable List<TipsList> data, int layoutRes) {
        super(data);
        this.context = context;
        this.layoutRes = layoutRes;
    }

    @Override
    protected AdapterItem createItem(Object type) {
        return new ViewPagerItem(layoutRes);
    }

    class ViewPagerItem extends AdapterItem<TipsList> {
       ImageView image;
        int layoutRes;
        ViewPagerItem(int layoutRes){
            this.layoutRes = layoutRes;
        }

        @Override
        public int getLayoutResId() {
            return layoutRes;
        }

        @Override
        public void onBindViews(View root) {
            image = ((ImageView) root.findViewById(R.id.image));
        }

        @Override
        public void onSetViews() {

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipsList tipsList = getModel();
//                    if(tipsList.toType==1){//关联类型 1-动态 2-专题 3-活动 4宠物店 6活动类型
//                        ARouterUtils.toH5WebViewActivity((Activity) context,1,tipsList.toObject,context.getResources().getString(R.string.title_detail),"");
//                    }else if(tipsList.toType==2){
//                        ARouterUtils.toH5WebViewActivity((Activity) context,4,tipsList.toObject,"","");
//                    }else if(tipsList.toType==3){
//                        ARouterUtils.toH5WebViewActivity((Activity) context,3,tipsList.toObject,"","");
//                    }else if(tipsList.toType==4){
//                        ARouterUtils.toPetShopActivity((Activity) context,tipsList.toObject);
//                    }else if(tipsList.toType==5){
//                        ARouterUtils.toH5WebViewActivity((Activity) context,2,tipsList.toObject,"","");
//                    }else if(tipsList.toType==6){
//                        ARouterUtils.toCityActivity((Activity) context,tipsList.toObject,tipsList.title);
//                    }else if(tipsList.toType==7){
//                        ARouterUtils.toH5WebViewActivity((Activity) context,5,"",tipsList.title,tipsList.toObject);
//                    }else if(tipsList.toType==9){
//                        ARouterUtils.toH5WebViewActivity((Activity) context,100,tipsList.toObject,"","");
//                    }
                }
            });
        }

        @Override
        public void onUpdateViews(TipsList o, int position) {
            if(getData().size() <= position) return;
//TODO 显示图片
            String path = getData().get(position).imageUrl;
//            ImageUtils.loadImageWithoutError(GetPictureUrlUtils.GetPicturefirstUrl(path,1000,0,"webp"),image);

            if (getData().get(position).id==0){
                image.setBackgroundDrawable(context.getApplicationContext().getResources().getDrawable(R.mipmap.icon_home_banner));
//                image.setBackgroundResource(R.mipmap.icon_home_banner);
//                image.setBackgroundResource(icon_home_banner);
            }else if(getData().get(position).id==1){
                image.setBackgroundDrawable(context.getApplicationContext().getResources().getDrawable(R.mipmap.icon_market));
//                image.setBackgroundResource(R.mipmap.icon_market);
            }else if(getData().get(position).id==2){
                image.setBackgroundDrawable(context.getApplicationContext().getResources().getDrawable(R.mipmap.icon_laowu_banner));
//                image.setBackgroundResource(R.mipmap.icon_laowu_banner);
            }else{
//                image.setBackgroundResource(R.mipmap.icon_home_banner);
                image.setBackgroundDrawable(context.getApplicationContext().getResources().getDrawable(R.mipmap.icon_home_banner));
//                image.setBackgroundResource(R.mipmap.icon_home_banner);
            }
        }
    }
}
