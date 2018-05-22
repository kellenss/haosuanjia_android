package suanhang.jinan.com.suannihen.bean;

import java.io.Serializable;

/** 
 *    
 */
public class TipsList implements Serializable {

	public String createTime;//创建时间
	public String createUser;//创建用户
	public int id;//id
	public String imageUrl;//tip图片
	public String modifyUser;//
	public int status;//1 - 待上线 2 － 上线 3 － 下线
	public String title;//标题
	public String toCity;//发布到 各个城市ID ,分割
	public String toObject;//关联id
	public int toType;//
	public int yn;//
}
