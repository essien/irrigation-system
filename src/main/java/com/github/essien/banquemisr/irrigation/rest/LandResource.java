package com.github.essien.banquemisr.irrigation.rest;

import com.github.essien.banquemisr.irrigation.dto.LandConfigurationDto;
import com.github.essien.banquemisr.irrigation.dto.LandCreationDto;
import com.github.essien.banquemisr.irrigation.dto.LandDto;
import com.github.essien.banquemisr.irrigation.dto.LandModificationDto;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import com.github.essien.banquemisr.irrigation.out.PageOutput;
import com.github.essien.banquemisr.irrigation.out.Response;
import com.github.essien.banquemisr.irrigation.service.LandService;
import javax.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bodmas
 * @since Nov 24, 2022.
 */
@RestController
@RequestMapping(
        value = "/api/v1/lands",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LandResource {

    private final LandService landService;
    private final MapperFacade mapper;

    public LandResource(LandService landService, MapperFacade mapper) {
        this.landService = landService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Response> add(@Valid @RequestBody LandCreationDto landDto) {
        landService.create(mapper.map(landDto, LandModel.class));
        return RestUtil.created("land", landDto);
    }

    @PutMapping("/{landId}/configure")
    public ResponseEntity<Response> configure(@PathVariable String landId, @Valid @RequestBody LandConfigurationDto configuration) {
        configuration.setLandId(landId);
        landService.configure(mapper.map(configuration, LandModel.class));
        return RestUtil.updated("land configuration", configuration);
    }

    @PatchMapping("/{landId}")
    public ResponseEntity<Response> edit(@PathVariable String landId, @Valid @RequestBody LandModificationDto modification) {
        modification.setLandId(landId);
        LandModel landModel = landService.update(mapper.map(modification, LandModel.class));
        return RestUtil.updated("land", mapper.map(landModel, LandDto.class));
    }

    @GetMapping
    public ResponseEntity<Response> getPaginated(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "50") Integer size) {
        PageModel<LandModel> pageModel = landService.getAll(page, size);
        return RestUtil.retrievedMany("land", "lands",
                                      new PageOutput(pageModel, landModel -> mapper.mapAsList(landModel, LandDto.class)));
    }
}
