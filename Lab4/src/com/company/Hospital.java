package com.company;

import org.omg.CORBA.ShortSeqHelper;

import java.util.ArrayList;
import java.util.List;

public class Hospital implements Comparable {
    String name;
    int capacity;


    public Hospital(String name, int capacity)
    {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return  name;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException();
        Hospital h = (Hospital) o;
        return this.name.compareTo(h.name);
    }

}
