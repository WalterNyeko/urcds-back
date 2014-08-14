package com.sweroad.util;

import java.util.List;

public class RcdsUtil<T> {

	public boolean itemExistsInList(T item, List<T> items) {
		for (T it : items) {
			if (it.equals(item)) {
				return true;
			}
		}
		return false;
	}
}
