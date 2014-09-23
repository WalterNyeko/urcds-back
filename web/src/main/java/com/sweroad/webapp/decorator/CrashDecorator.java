package com.sweroad.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.sweroad.model.Crash;
import com.sweroad.webapp.util.HtmlHelper;

public class CrashDecorator extends TableDecorator {

	public String getActions() {
		Crash crash = getCrash();
		long id = crash.getId();
		String viewLink = HtmlHelper.createAnchor("View", "crashview?id=" + id,
				"View crash", null);
		String editLink = HtmlHelper.createAnchor("Edit", "crashform?id=" + id,
				"Edit crash", null);
		String removeLink = HtmlHelper.createAnchor("Remove", "crashremove?id="
				+ id, "Remove crash", "return confirm('Remove crash?');");
		String crashActions = String.format("%s|%s|%s", viewLink, editLink,
				removeLink);
		return crashActions;
	}

	private Crash getCrash() {
		return (Crash) getCurrentRowObject();
	}
}
