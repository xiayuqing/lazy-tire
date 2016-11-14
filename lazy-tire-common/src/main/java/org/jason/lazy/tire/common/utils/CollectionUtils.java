package org.jason.lazy.tire.common.utils;


import org.jason.lazy.tire.common.bean.StringPair;
import org.jason.lazy.tire.common.bean.ValuePair;

import java.util.*;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class CollectionUtils {
    public static <T> List<T> singleToList(T o1) {
        if (null == o1) {
            return null;
        }

        List<T> result = new ArrayList<T>();
        result.add(o1);
        return result;
    }

    private static boolean objectEquals(Object o1, Object o2) {
        if (null == o1 && null == o2) {
            return true;
        }

        if (null == o1 || null == o2) {
            return false;
        }

        return o1.equals(o2);
    }

    public static boolean isEmpty(Collection<?> col) {
        return null == col || col.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static Map<String, String> toStringMap(StringPair[] pairs) {
        Map<String, String> result = new HashMap<String, String>();
        if (0 != pairs.length) {
            for (int i = 0; i < pairs.length; i++) {
                result.put(pairs[i].getKey(), pairs[i].getValue());
            }
        }

        return result;
    }

    public static <K, V> Map<K, V> toMap(ValuePair<K, V>... pairs) {
        Map<K, V> result = new HashMap<K, V>();
        if (null == pairs || 0 == pairs.length) {
            return result;
        }

        for (int i = 0; i < pairs.length; i++) {
            result.put(pairs[i].getKey(), pairs[i].getValue());
        }

        return result;
    }
}
