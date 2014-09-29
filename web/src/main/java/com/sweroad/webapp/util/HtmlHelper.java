package com.sweroad.webapp.util;

public class HtmlHelper {

	/**
	 * Html anchor with placeholders for href, alt and onclick
	 */
	public static final String HTML_ANCHOR_TAG = "<a href=\"%s\"%s%s>";

	/**
	 * Html image tag with placeholders for src, alt, title and hspace
	 */
	public static final String HTML_IMAGE_TAG = "<img src=\"%s\"%s%s />";

	/**
	 * Html achor close tag
	 */
	public static final String HTML_ANCHOR_TAG_CLOSE = "</a>";

	public static String createAnchor(String innerText, String href,
			String alt, String onclick) {
		if (alt != null && !alt.equals("")) {
			alt = " alt=\"" + alt + "\"";
		} else {
			alt = "";
		}
		if (onclick != null && !onclick.equals("")) {
			onclick = " onclick=\"" + onclick + "\"";
		} else {
			onclick = "";
		}
		String htmlAnchor = String.format(HTML_ANCHOR_TAG, href, alt, onclick);
		htmlAnchor += innerText + HTML_ANCHOR_TAG_CLOSE;
		return htmlAnchor;
	}

	public static String createImage(String imagePath, String alt, String hspace) {
		if (alt != null && !alt.equals("")) {
			alt = " alt=\"" + alt + "\" title=\"" + alt + "\"";
		} else {
			alt = "";
		}
		if (hspace != null && !hspace.equals("")) {
			hspace = " hspace=\"" + hspace + "\"";
		} else {
			hspace = "";
		}
		return String.format(HTML_IMAGE_TAG, imagePath, alt, hspace);		
	}
}