package com.viger.gfJdmall.listener;

/**
 *	只要是容器 都可以实现它
 */
public interface IViewContainer {

	/**
	 * 让容器显示界面
	 */
	public void onShow(Object... values);
}
