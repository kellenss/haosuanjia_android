package com.jinan.haosuanjia.utils;

/**
 * @author
 * @time 2015-11-13
 */
public class LiteHttp {

    protected SmartExecutor smartExecutor;
    
	private LiteHttp(){
		smartExecutor = new SmartExecutor();
	}
	
	public static LiteHttp getInstence(){
		return new LiteHttp();
	}
	
	
	 public void executeAsync(Runnable request) {
	        smartExecutor.execute(request);
	    }

}
