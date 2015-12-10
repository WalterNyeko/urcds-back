package com.sweroad.util;

import java.util.List;

public class ListUtil<T> {

	public boolean itemExistsInList(T item, List<T> items) {
        return items.stream()
                .filter(x -> x.equals(item))
                .count() > 0;
	}
}
