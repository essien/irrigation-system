package com.github.essien.banquemisr.irrigation.service.impl;

import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.entity.WaterConfig;
import com.github.essien.banquemisr.irrigation.exception.LandNotFoundException;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import com.github.essien.banquemisr.irrigation.repo.LandRepository;
import com.github.essien.banquemisr.irrigation.service.LandService;
import java.util.List;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link LandService}.
 * <p>
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Service
public class LandServiceImpl implements LandService {

    private static final Logger log = LoggerFactory.getLogger(LandServiceImpl.class);

    private final LandRepository landRepository;
    private final MapperFacade mapperFacade;

    public LandServiceImpl(LandRepository landRepository, MapperFacade mapperFacade) {
        this.landRepository = landRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public void create(LandModel landModel) {
        Land land = new Land();
        land.setKey(landModel.getLandId());
        land.setArea(landModel.getArea());
        landRepository.save(land);
    }

    @Override
    public void configure(LandModel landModel) {
        Land land = getLand(landModel.getLandId());
        List<WaterConfig> waterConfigs = mapperFacade.mapAsList(landModel.getWaterConfigs(), WaterConfig.class);
        land.setWaterConfigs(waterConfigs);
        landRepository.save(land);
    }

    @Override
    public LandModel update(LandModel landModel) {
        Land land = getLand(landModel.getLandId());
        land.setArea(landModel.getArea());
        landRepository.save(land);
        return mapperFacade.map(land, LandModel.class);
    }

    private Land getLand(String key) {
        return landRepository.findByKey(key).orElseThrow(() -> new LandNotFoundException(key));
    }

    @Override
    public PageModel<LandModel> getAll(int page, int size) {
        return new PageModel<>(
                mapperFacade.mapAsList(landRepository.findAll(PageRequest.of(page, size)), LandModel.class), page, size
        );
    }
}
