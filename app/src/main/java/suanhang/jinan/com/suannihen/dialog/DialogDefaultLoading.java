package suanhang.jinan.com.suannihen.dialog;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import suanhang.jinan.com.suannihen.R;


public class DialogDefaultLoading extends Dialog {

    public DialogDefaultLoading(Context context) {
        this(context, R.style.CustomProgressDialog);
    }

    String title;
    public DialogDefaultLoading(Context context, String title) {
        this(context, R.style.CustomProgressDialog);
        this.title = title;
    }

    public DialogDefaultLoading(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.custom_progress_loading);
        setCanceledOnTouchOutside(false);
        if(!TextUtils.isEmpty(title)){
            ((TextView) findViewById(R.id.tv_tips)).setText(title);
        }
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            Activity activity = null;
            if (!(context instanceof Application))
                if (context instanceof ContextThemeWrapper) {
                    activity = (Activity) ((ContextThemeWrapper) context).getBaseContext();
                }
            if(activity != null && !activity.isFinishing() && !isShowing())
                super.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            Context context = getContext();
            Activity activity = null;
            if (!(context instanceof Application))
                if (context instanceof ContextThemeWrapper) {
                    activity = (Activity) ((ContextThemeWrapper) context).getBaseContext();
                }
            if(activity != null && !activity.isFinishing() && isShowing())
                super.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
