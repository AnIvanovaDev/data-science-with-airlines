package com.ivanova.util;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Anastasiia Ivanova
 * @since 1.0.0
 */
public class Pair<K, V> {

    private K key;
    private V value;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }


    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }

    public static <K, V> Comparator<Pair<K, V>> comparingByKey(Comparator<? super K> cmp) {
        Objects.requireNonNull(cmp);
        return (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
    }

    public static <K, V extends Comparable<? super V>> Comparator<Pair<K,V>> comparingByValue() {
        return (Comparator<Pair<K, V>>)
                (c1, c2) -> c1.getValue().compareTo(c2.getValue());
    }

    public static <K extends Comparable<? super K>, V> Comparator<Pair<K,V>> comparingByKey() {
        return (Comparator<Pair<K, V>>)
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
    }

    public static <K, V> Comparator<Pair<K, V>> comparingByValue(Comparator<? super V> cmp) {
        Objects.requireNonNull(cmp);
        return (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

    @Override
    public String toString() {
        return key + ", " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
