package com.sweroad.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ListUtilTest {

	private List<String> stringList;
	private String string;
	private ListUtil<String> listUtil;
	
	@Before
	public void setUp(){
		stringList = new ArrayList<String>();
		string = "test string";
		stringList.add(string);
		stringList.add("another string");
		stringList.add("another string");
		listUtil = new ListUtil<String>();
	}
	
	@Test
	public void testThatItemExistsInList(){
		assertTrue(listUtil.itemExistsInList(string, stringList));
	}
}
