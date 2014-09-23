package com.sweroad.webapp.util;

public class HtmlHelper {

	/**
	 * Html anchor with placeholders for href, alt and onclick
	 */
	public static final String HTML_ANCHOR_TAG = "<a href=\"%s\"%s%s>";

	/**
	 * Html achor close tag
	 */
	public static final String HTML_ANCHOR_TAG_CLOSE = "</a>";

	public static String createAnchor(String innerText, String href,
			String alt, String onclick) {
		if (alt != null) {
			alt = " alt=\"" + alt + "\"";
		} else {
			alt = "";
		}
		if (onclick != null) {
			onclick = " onclick=\"" + onclick + "\"";
		} else {
			onclick = "";
		}
		String htmlAnchor = String.format(HTML_ANCHOR_TAG, href, alt, onclick);
		htmlAnchor += innerText + HTML_ANCHOR_TAG_CLOSE;
		return htmlAnchor;
	}
}