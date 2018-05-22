package suanhang.jinan.com.suannihen.interfac;

public interface VolleyResult {
	void success(String result, String method);

	void failure(String error, String method);
}
