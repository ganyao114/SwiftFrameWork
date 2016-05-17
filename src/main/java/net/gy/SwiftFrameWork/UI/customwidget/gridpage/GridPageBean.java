package net.gy.SwiftFrameWork.UI.customwidget.gridpage;

import android.view.View;

import java.io.Serializable;

public class GridPageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int PAGE_NUM = 5;
	private String[] title;
	private View[] content;
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public View[] getContent() {
		return content;
	}

	public void setContent(View[] content) {
		this.content = content;
	}
}
