package suanhang.jinan.com.suannihen.request;

import org.json.JSONObject;

/**
 * Created by admin on 2016/07/01.
 * Function：
 * Modify by admin on 2016/07/01.
 * Modify Reason：
 */
public abstract class BaseHandlerJsonObject {

    public abstract void onGotJson(JSONObject result);

    public abstract void onGotError(String code, String error);
}
