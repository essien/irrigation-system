package com.github.essien.banquemisr.irrigation.service;

import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
public interface LandService {

    /**
     * Add new plot of land.
     * @param landModel land model with creation details
     */
    void create(LandModel landModel);

    /**
     * Configure a plot of land.
     * @param landModel land model with configuration details
     */
    void configure(LandModel landModel);

    /**
     * Update a plot of land.
     * @param landModel land model with modification details
     * @return the updated object
     */
    LandModel update(LandModel landModel);

    /**
     * Retrieve all plots of land and their details.
     * @param page the page to retrieve
     * @param size the size of entries in each page
     * @return the plots of land satisfying the pagination criteria
     */
    PageModel<LandModel> getAll(int page, int size);
}
