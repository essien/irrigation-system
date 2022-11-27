package com.github.essien.banquemisr.irrigation.config;

import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.entity.WaterConfig;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import java.time.LocalTime;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Configuration
public class MapperFactory {

    @Bean
    public MapperFacade mapperFacade() {
        ma.glasnost.orika.MapperFactory factory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        ConverterFactory converterFactory = factory.getConverterFactory();

        converterFactory.registerConverter(new PassThroughConverter(LocalTime.class));
        converterFactory.registerConverter(new CustomConverter<Land, LandModel>() {
            @Override
            public LandModel convert(Land land, Type<? extends LandModel> destinationType, MappingContext mappingContext) {
                LandModel landModel = LandModel.builder().withLandId(land.getKey()).withArea(land.getArea())
                        .withWaterConfigs(mapperFacade.mapAsList(land.getWaterConfigs(), LandModel.WaterConfig.class)).build();
                mapperFacade.map(land, landModel);
                return landModel;
            }
        });

        converterFactory.registerConverter(new BidirectionalConverter<LandModel.WaterConfig, WaterConfig>() {
            @Override
            public LandModel.WaterConfig convertFrom(WaterConfig d, Type<LandModel.WaterConfig> type, MappingContext mc) {
                return LandModel.WaterConfig.builder()
                        .withCron(d.getCron())
                        .withDuration(d.getDuration())
                        .withAmountOfWater(d.getWaterQuantity())
                        .build();
            }

            @Override
            public WaterConfig convertTo(LandModel.WaterConfig source, Type<WaterConfig> type, MappingContext mc) {
                WaterConfig waterConfig = new WaterConfig();
                waterConfig.setCron(source.getCron());
                waterConfig.setDuration(source.getDuration());
                waterConfig.setWaterQuantity(source.getAmountOfWater());
                return waterConfig;
            }
        });
        return factory.getMapperFacade();
    }
}
