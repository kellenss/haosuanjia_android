package suanhang.jinan.com.suannihen.utils;

import android.content.Context;
import android.widget.Toast;

public class ShowToastUtil {

    public static void Short(CharSequence sequence) {
        Toast.makeText(HMApplication.application, sequence, Toast.LENGTH_SHORT).show();
    }

    public static void Short(int stringId) {
        Toast.makeText(HMApplication.application, stringId, Toast.LENGTH_SHORT).show();
    }

    public static void Long(CharSequence sequence) {
        Toast.makeText(HMApplication.application, sequence, Toast.LENGTH_LONG).show();
    }

    public static void toastShow(String text) {
        if (ConstantString.toast == null) {
            ConstantString.toast = Toast.makeText(HMApplication.application, text, Toast.LENGTH_SHORT);
        } else {
            ConstantString.toast.setText(text);
        }
        ConstantString.toast.show();
    }
    public static void toastShow(String text, Context context) {
        toastShow(text);
    }
}
