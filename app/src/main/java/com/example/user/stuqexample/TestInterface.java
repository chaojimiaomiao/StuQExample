package com.example.user.stuqexample;

/**
 * Created by zhaibingjie on 16/9/1.
 */
public interface TestInterface {

    public abstract void count(int i);

    String toString(); //same to Object.toString
    int hashCode(); //same to Object.hashCode
    boolean equals(Object obj); //same to Object.equals
}

