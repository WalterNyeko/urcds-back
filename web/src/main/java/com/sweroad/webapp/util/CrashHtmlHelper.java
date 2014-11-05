package com.sweroad.webapp.util;

public class CrashHtmlHelper extends HtmlHelper {

	public static String createViewLink(long crashId) {
		String viewIcon = createImage("/images/bt_view.gif", "View", "4");
		String viewLink = createAnchor(viewIcon, "crashview?id=" + crashId, "View crash", null);
		return viewLink;
	}

	public static String createEditLink(long crashId) {
		String editIcon = HtmlHelper.createImage("/images/bt_edit.gif", "Edit", "4");
		String editLink = HtmlHelper.createAnchor(editIcon, "crashform?id=" + crashId, "Edit crash", null);
		return editLink;
	}

	public static String createRemoveLink(long crashId) {
		String removeIcon = HtmlHelper.createImage("/images/bt_remove.gif", "Remove", "4");
		String removeLink = HtmlHelper.createAnchor(removeIcon, "crashremove?id=" + crashId, "Remove crash",
				"return confirm('Remove crash?');");
		return removeLink;
	}
	
	public static String createRestoreLink(long crashId) {
		String removeIcon = HtmlHelper.createImage("/images/bt_restore.gif", "Restore", "4");
		String removeLink = HtmlHelper.createAnchor(removeIcon, "crashrestore?id=" + crashId, "Restore crash",
				"return confirm('Restore removed crash?');");
		return removeLink;
	}
}