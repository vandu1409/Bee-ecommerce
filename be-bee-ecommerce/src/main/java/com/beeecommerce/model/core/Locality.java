package com.beeecommerce.model.core;

public interface Locality extends Comparable<Locality> {

    @Override
    default int compareTo(Locality o) {
        return this.getName().compareTo(o.getName());
    }

    String getName();

    String getCode();

    Long getId();

}
