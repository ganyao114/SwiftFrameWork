package net.gy.SwiftFrameWork.UI.customwidget.mypageview;

import java.io.Serializable;

public class PagerViewBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306825507171445344L;
	public static int PAGE_NUM = 5;
	private String[] imgsrc = new String[PAGE_NUM];
	private String[] title = new String[PAGE_NUM];
	public String[] getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String[] imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
}
