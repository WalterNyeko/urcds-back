package com.sweroad.webapp.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlHelperTest {

	@Test
	public void testThatHtmlAnchorWorks() {
		String expected = "<a href=\"http://www.sweroad.com\" alt=\"Go to Sweroad.com\" onclick=\"javascript:addToSweroadVisitors()\">Click here</a>";
		assertEquals(expected, HtmlHelper.createAnchor("Click here",
				"http://www.sweroad.com", "Go to Sweroad.com",
				"javascript:addToSweroadVisitors()"));
	}
}
