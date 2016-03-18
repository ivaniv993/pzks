package com.luxoft.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIvaniv on 05.10.2015.
 */
public class Page<E> {

    private List<E> items = new ArrayList<>();

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private int pageNumber;

}
