package com.github.essien.banquemisr.irrigation.config;

import com.github.essien.banquemisr.irrigation.Constants;
import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.entity.WaterConfig;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
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

    private static <A, B> void registerDefaultMapping(ma.glasnost.orika.MapperFactory factory, Class<A> classA, Class<B> classB,
                                                      XYZ1<A, B> aToB, XYZ2<A, B> bToA, List<Map.Entry<String, String>> fieldMap,
                                                      String... excludes) {
        ClassMapBuilder<A, B> mapping = mapping(factory, classA, classB, aToB, bToA);
        for (String exclude : excludes)
            mapping.exclude(exclude);
        for (Map.Entry<String, String> field : fieldMap)
            mapping.field(field.getKey(), field.getValue());
        mapping.byDefault().register();
    }

    private static <A, B> void registerDefaultMapping(ma.glasnost.orika.MapperFactory factory, Class<A> classA, Class<B> classB,
                                                      XYZ1<A, B> aToB, XYZ2<A, B> bToA, String... excludes) {
        registerDefaultMapping(factory, classA, classB, aToB, bToA, Collections.emptyList(), excludes);
    }

    private static <A, B> void registerMapping(ma.glasnost.orika.MapperFactory factory, Class<A> classA, Class<B> classB,
                                               XYZ1<A, B> aToB, XYZ2<A, B> bToA) {
        mapping(factory, classA, classB, aToB, bToA).register();
    }

    private static <A, B> ClassMapBuilder<A, B> mapping(ma.glasnost.orika.MapperFactory factory, Class<A> classA, Class<B> classB,
                                                        XYZ1<A, B> aToB, XYZ2<A, B> bToA) {
        final ClassMapBuilder<A, B> customize = factory.classMap(classA, classB)
                .customize(new CustomMapper<A, B>() {
                    @Override
                    public void mapAtoB(A a, B b, MappingContext context) {
                        if (aToB != null)
                            aToB.mapAtoB(a, b);
                    }

                    @Override
                    public void mapBtoA(B b, A a, MappingContext context) {
                        if (bToA != null)
                            bToA.mapBtoA(b, a);
                    }
                });
        return customize;
    }

    private static interface XYZ1<A, B> {

        void mapAtoB(A a, B b);
    }

    private static interface XYZ2<A, B> {

        void mapBtoA(B b, A a);
    }

    private static Map.Entry<String, String> map(String a, String b) {
        return new AbstractMap.SimpleEntry<>(a, b);
    }
}
