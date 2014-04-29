package com.manydesigns.portofino.actions.echarts;

import java.io.StringReader;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/actions/echarts/ajaxTest")
public class AjaxTestActionBean implements ActionBean {
	private String name;
	private String describ;
	private String result;
	private ActionBeanContext context;

	@DefaultHandler
	public Resolution subAjax() {
		result = "Name is：" + name;
		result = result + "<br/>";
		result = result + "Description:" + describ;
		return new StreamingResolution("text", new StringReader(result));
	}

	public String getResult() {
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescrib(String describ) {
		this.describ = describ;
	}

	public ActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext arg0) {
		this.context = arg0;
	}

}