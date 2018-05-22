package suanhang.jinan.com.suannihen.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import suanhang.jinan.com.suannihen.utils.VolleyUtils;

/**
 * Created by admin on 2017/6/23.
 */

public class VolleyBaseString extends StringRequest {
    Map<String, String> map;
    public VolleyBaseString(Map<String, String> map, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.map = map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        // TODO Auto-generated method stub
        return VolleyUtils.initVolleyHeader(map);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = VolleyUtils.parseZipResponse(response);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
