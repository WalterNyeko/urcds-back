package com.sweroad.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.sweroad.model.Crash;
import com.sweroad.webapp.util.HtmlHelper;

public class CrashDecorator extends TableDecorator {

	public String getActions() {
		Crash crash = getCrash();
		long id = crash.getId();
		String viewIcon = HtmlHelper.createImage("/images/bt_View.gif", "View",
				"4");
		String viewLink = HtmlHelper.createAnchor(viewIcon, "crashview?id="
				+ id, "View crash", null);
		String editIcon = HtmlHelper.createImage("/images/bt_Edit.gif", "Edit",
				"4");
		String editLink = HtmlHelper.createAnchor(editIcon, "crashform?id="
				+ id, "Edit crash", null);
		String removeIcon = HtmlHelper.createImage("/images/bt_Remove.gif",
				"Remove", "4");
		String removeLink = HtmlHelper.createAnchor(removeIcon,
				"crashremove?id=" + id, "Remove crash",
				"return confirm('Remove crash?');");
		String crashActions = String.format("%s%s%s", viewLink, editLink,
				removeLink);
		return crashActions;
	}

	private Crash getCrash() {
		return (Crash) getCurrentRowObject();
	}
}
