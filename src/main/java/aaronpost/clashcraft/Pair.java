package aaronpost.clashcraft;

// Author: Aaron Post
public class Pair<K,V> implements Cloneable {
    public K first;
    public V second;
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    public Pair<K,V> clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return new Pair<K,V>(this.first,this.second);
    }
}
