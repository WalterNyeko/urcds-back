package com.sweroad.util;

import com.sweroad.query.Queryable;
import com.sweroad.service.GenericManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 3/3/15.
 */
public class GenericManagerHelper {

    public static <T extends Queryable> List<T> filterForCrashSearch(List<T> listToFilter, GenericManager listManager) {
        List<T> nonNullList = stripNulls(listToFilter);
        return nonNullList.size() > 0 ? getFilteredList(nonNullList, listManager) : nonNullList;
    }

    private static <T extends Queryable> List<T> getFilteredList(List<T> list, GenericManager listManager) {
        List<T> dbList = listManager.getAllDistinct();
        return dbList.stream().filter(t -> list.contains(t)).collect(Collectors.toList());
    }

    private static <T extends Queryable> List<T> stripNulls(List<T> list) {
        List<T> strippedList = new ArrayList<>();
        if (list != null) {
            strippedList = list.stream().filter(t -> t.getId() != null).collect(Collectors.toList());
        }
        return strippedList;
    }
}