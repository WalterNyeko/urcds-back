package com.sweroad.util;

import com.sun.tools.jdi.*;

import java.util.*;
import java.util.LinkedHashMap;

/**
 * Created by Frank on 11/8/14.
 */
public class MapUtil
{
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K,V>
        reverseSortByValue(Map<K,V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K,V>>(map.entrySet());
        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }));

        Map<K, V> result = new LinkedHashMap<K,V>();
        for(Map.Entry<K,V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}


