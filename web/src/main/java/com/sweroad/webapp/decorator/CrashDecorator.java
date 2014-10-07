package com.sweroad.webapp.decorator;

import org.displaytag.decorator.TableDecorator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;
import com.sweroad.webapp.util.CrashHtmlHelper;

public class CrashDecorator extends TableDecorator {

	@Autowired
	private CrashManager crashManager;

	public String getActions() {
		Crash crash = getCrash();
		long id = crash.getId();
		String viewLink = CrashHtmlHelper.createViewLink(id);
		String editLink = crash.isEditable() ? CrashHtmlHelper.createEditLink(id) : "";
		String removeLink = crash.isRemovable() ? CrashHtmlHelper.createRemoveLink(id) : "";
		String crashActions = String.format("%s%s%s", viewLink, editLink, removeLink);
		return crashActions;
	}

	private Crash getCrash() {
		return (Crash) getCurrentRowObject();
	}
}