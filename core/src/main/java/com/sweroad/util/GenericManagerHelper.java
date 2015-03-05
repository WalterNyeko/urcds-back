package com.sweroad.util;

import com.sweroad.model.LabelValue;
import com.sweroad.query.Queryable;
import com.sweroad.service.GenericManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 3/3/15.
 */
public class GenericManagerHelper {

    public static <T extends Queryable> List<T> filterForCrashSearch(List<T> listToFilter, GenericManager listManager) {
        List<T> nonNullList = stripNulls(listToFilter);
        return nonNullList.size() > 0 ? getFilteredList(nonNullList, listManager) : nonNullList;
    }

    private static <T extends Queryable> List<T> getFilteredList(List<T> list, GenericManager listManager) {
        List<T> dbList = listManager.getAll();
        List<T> filteredList = new ArrayList<T>();
        for (T t : dbList) {
            for (T y : list) {
                if (t.getId().equals(y.getId())) {
                    filteredList.add(t);
                }
            }
        }
        return filteredList;
    }

    private static <T extends Queryable> List<T> stripNulls(List<T> list) {
        List<T> strippedList = new ArrayList<T>();
        if (list != null) {
            for (T t : list) {
                if (t.getId() != null) {
                    strippedList.add(t);
                }
            }
        }
        return strippedList;
    }
}
