package aaronpost.clashcraft;

// Author: Aaron Post
public class Pair<K,V> {
    public K first;
    public V second;
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    public Pair<K,V> clone() {
        return new Pair<K,V>(this.first,this.second);
    }
}
