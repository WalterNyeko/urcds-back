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

	/**
	 * Creates html anchor tag based on the passed attributes.
	 * 
	 * @param innerText
	 *            Inner text of 'a' tag
	 * @param href
	 *            link to be loaded on click
	 * @param alt
	 *            Tool tip text
	 * @param onclick
	 *            javascript to execute on click
	 * @return html 'a' tag
	 */
	public static String createAnchor(String innerText, String href, String alt, String onclick) {
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

	/**
	 * Creates html image tag.
	 * 
	 * @param imagePath
	 *            Path of image
	 * @param alt
	 *            Tool tip
	 * @param hspace
	 *            Image hspace attribute
	 * @return html image tag
	 */
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