package com.sweroad.webapp.util;

import com.mysql.jdbc.StringUtils;

public class HtmlHelper {

    /**
     * Html anchor with placeholders for href, alt and onclick
     */
    public static final String HTML_ANCHOR_TAG = "<a href=\"%s\"%s%s%s>";

    /**
     * Html image tag with placeholders for src, alt, title and hspace
     */
    public static final String HTML_IMAGE_TAG = "<img src=\"%s\"%s%s%s%s />";

    /**
     * Html achor close tag
     */
    public static final String HTML_ANCHOR_TAG_CLOSE = "</a>";

    /**
     * Creates html anchor tag based on the passed attributes.
     *
     * @param innerText Inner text of 'a' tag
     * @param href      link to be loaded on click
     * @param alt       Tool tip text
     * @param onclick   javascript to execute on click
     * @return html 'a' tag
     */
    public static String createAnchor(String innerText, String href, String alt, String onclick, String... dataAttributes) {
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
        String htmlAnchor = String.format(HTML_ANCHOR_TAG, href, alt, onclick, buildDataAttributes(dataAttributes));
        htmlAnchor += innerText + HTML_ANCHOR_TAG_CLOSE;
        return htmlAnchor;
    }

    private static String buildDataAttributes(String... dataAttributes) {
        String dataAttribute = "";
        for(String data : dataAttributes) {
            dataAttribute += data.concat(" ");
        }
        return dataAttribute;
    }

    /**
     * Creates html image tag.
     *
     * @param imagePath Path of image
     * @param alt       Tool tip
     * @param hspace    Image hspace attribute
     * @return html image tag
     */
    public static String createImage(String imagePath, String alt, String hspace, String height, String width) {
        if (alt != null && !alt.equals("")) {
            alt = " alt=\"" + alt + "\" title=\"" + alt + "\"";
        } else {
            alt = "";
        }
        if (!StringUtils.isNullOrEmpty(hspace)) {
            hspace = " hspace=\"" + hspace + "\"";
        } else {
            hspace = "";
        }
        if (!StringUtils.isNullOrEmpty(height)) {
            height = " height=\"" + height + "\"";
        } else {
            height = "";
        }
        if (!StringUtils.isNullOrEmpty(width)) {
            width = " width=\"" + width + "\"";
        } else {
            width = "";
        }
        return String.format(HTML_IMAGE_TAG, imagePath, alt, hspace, height, width);
    }
}