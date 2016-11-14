package org.jason.lazy.tire.common.bean;

/**
 * Created by Jason.Xia on 16/8/29.
 */
public class ValuePair<K, V> {
    private K key;
    private V value;

    public ValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
