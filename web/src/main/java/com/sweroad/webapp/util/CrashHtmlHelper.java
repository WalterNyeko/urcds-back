package com.sweroad.webapp.util;

public class CrashHtmlHelper extends HtmlHelper {

	public static String createViewLink(long crashId, String dataAttributeName) {
		String viewIcon = createImage("/images/bt_view.gif", "View", "4", null, null);
		String viewLink = createAnchor(viewIcon, "crashview?id=" + crashId, "View crash", "setAccessedObject(this)", dataAttributeName.concat("=") + crashId);
		return viewLink;
	}

	public static String createEditLink(long crashId) {
		String editIcon = HtmlHelper.createImage("/images/bt_edit.gif", "Edit", "4", null, null);
		String editLink = HtmlHelper.createAnchor(editIcon, "crashform?id=" + crashId, "Edit crash", "setAccessedObject(this)");
		return editLink;
	}

	public static String createRemoveLink(long crashId) {
		String removeIcon = HtmlHelper.createImage("/images/bt_remove.gif", "Remove", "4", null, null);
		String removeLink = HtmlHelper.createAnchor(removeIcon, "crashremove?id=" + crashId, "Remove crash",
				"setAccessedObject(this); return confirm('Remove crash?');");
		return removeLink;
	}
	
	public static String createRestoreLink(long crashId) {
		String removeIcon = HtmlHelper.createImage("/images/bt_restore.gif", "Restore", "4", null, null);
		String removeLink = HtmlHelper.createAnchor(removeIcon, "crashrestore?id=" + crashId, "Restore crash",
				"setAccessedObject(this); return confirm('Restore removed crash?');", "data-crashes-id=" + crashId);
		return removeLink;
	}

    public static String createMapViewLink(String crashNo, Double latitude, Double longitude, long crashId) {
        String mapIcon = HtmlHelper.createImage("/images/gglMap.png", "Show on Map", "4", "18", null);
        String onClick = String.format("setAccessedObject(this); return quickMapView('%s', %f, %f);", crashNo, latitude, longitude);
        String mapLink = HtmlHelper.createAnchor(mapIcon, "", "Show on Map", onClick);
        return mapLink;
    }
}