package com.github.essien.banquemisr.irrigation.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import java.io.Serializable;
import java.util.List;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<?> elements;
    private final int page;
    private final int size;

    public <T> PageOutput(PageModel<T> pageModel) {
        this.elements = pageModel.getElements();
        this.page = pageModel.getPage();
        this.size = pageModel.getSize();
    }

    public List<?> getElements() {
        return elements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
