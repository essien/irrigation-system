package com.github.essien.banquemisr.irrigation.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author bodmas
 * @param <T> the type of elements in this page
 * @since Nov 25, 2022.
 */
public class PageModel<T> {

    private final List<T> elements;
    private final int page;
    private final int size;

    public PageModel(List<T> elements, int page, int size) {
        this.elements = elements;
        this.page = page;
        this.size = size;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
