package com.company;

import java.util.ArrayList;
import java.util.List;

public class Resident implements Comparable {
    String name;


    public Resident( String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException();
        Resident h = (Resident) o;
        return this.name.compareTo(h.name);
    }
}
