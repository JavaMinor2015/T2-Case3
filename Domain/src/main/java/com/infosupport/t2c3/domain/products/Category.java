package com.infosupport.t2c3.domain.products;

/**
 * The category a Product can be in.
 */
public enum Category {

    BICYCLE("bicycle"),
    PART("part");

    private final String type;

    Category(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
