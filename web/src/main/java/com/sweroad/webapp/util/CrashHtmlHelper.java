package com.sweroad.webapp.util;

public class CrashHtmlHelper extends HtmlHelper {

	public static String createViewLink(long crashId, String dataAttributeName, String context) {
		String viewIcon = createImage(context + "/images/bt_view.gif", "View", "4", null, null);
		String viewLink = createAnchor(viewIcon, context + "/crashview?id=" + crashId, "View crash", "setAccessedObject(this)", dataAttributeName.concat("=") + crashId);
		return viewLink;
	}

	public static String createEditLink(long crashId, String context) {
		String editIcon = HtmlHelper.createImage(context + "/images/bt_edit.gif", "Edit", "4", null, null);
		String editLink = HtmlHelper.createAnchor(editIcon, context + "/crashform?id=" + crashId, "Edit crash", "setAccessedObject(this)");
		return editLink;
	}

	public static String createRemoveLink(long crashId, String context) {
		String removeIcon = HtmlHelper.createImage(context + "/images/bt_remove.gif", "Remove", "4", null, null);
		String removeLink = HtmlHelper.createAnchor(removeIcon, context + "/crashremove?id=" + crashId, "Remove crash",
				"setAccessedObject(this); return confirmDialog({message : 'Remove crash?', aLink : this});");
		return removeLink;
	}
	
	public static String createRestoreLink(long crashId, String context) {
		String removeIcon = HtmlHelper.createImage(context + "/images/bt_restore.gif", "Restore", "4", null, null);
		String removeLink = HtmlHelper.createAnchor(removeIcon, context + "/crashrestore?id=" + crashId, "Restore crash",
				"setAccessedObject(this); return confirmDialog({message : 'Restore removed crash?', aLink : this});", "data-crashes-id=" + crashId);
		return removeLink;
	}

    public static String createMapViewLink(String crashNo, Double latitude, Double longitude, long crashId, String context) {
        String mapIcon = HtmlHelper.createImage(context + "/images/gglMap.png", "Show on Map", "4", "18", null);
        String onClick = String.format("setAccessedObject(this); return quickMapView('%s', %f, %f);", crashNo, latitude, longitude);
        String mapLink = HtmlHelper.createAnchor(mapIcon, "", "Show on Map", onClick);
        return mapLink;
    }

    public static String createCrashCodeLink(long crashId, String crashCode, String context) {
        String viewLink = createAnchor(crashCode, context + "/crashview?id=" + crashId, "View crash", "", "");
        return viewLink;
    }
}