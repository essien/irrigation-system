package com.github.essien.banquemisr.irrigation.service.impl;

import static com.github.essien.banquemisr.irrigation.Constants.ZONE_GMT;
import com.github.essien.banquemisr.irrigation.config.TestConfig;
import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.entity.WaterConfig;
import com.github.essien.banquemisr.irrigation.exception.LandNotFoundException;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import com.github.essien.banquemisr.irrigation.repo.LandRepository;
import com.github.essien.banquemisr.irrigation.service.LandService;
import java.time.ZoneId;
import java.time.LocalTime;
import java.util.Arrays;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for {@link LandServiceImpl}.
 *
 * @author bodmas
 * @since Nov 26, 2022.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestConfig.class)
@Transactional(propagation = Propagation.NEVER)
public class LandServiceImplTest {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private MapperFacade mapperFacade;

    private LandService landService;

    @Before
    public void setUp() {
        landService = new LandServiceImpl(landRepository, mapperFacade);
    }

    @After
    public void tearDown() {
        landRepository.deleteAll();
    }

    @Test
    public void createWhenAllIsWellShouldPass() {
        {
            // Arrange
            final String key = "first-id";
            assertThat(landRepository.findByKey(key)).isEmpty();

            LandModel landModel = LandModel.builder().withLandId(key).build();

            // Act
            landService.create(landModel);

            // Assert
            Land land = landRepository.findByKey(key).orElse(null);
            assertThat(land).isNotNull();
            assertThat(land.getCreatedAt()).isNotNull();
            assertThat(land.getUpdatedAt()).isNotNull();
            assertThat(land.getArea()).isNull();
        }
        {
            // Arrange
            final String key = "second-id";
            LandModel landModel = LandModel.builder().withLandId(key).withArea(5.0).build();

            // Act
            landService.create(landModel);

            // Assert
            Land land = landRepository.findByKey(key).orElse(null);
            assertThat(land).isNotNull();
            assertThat(land.getArea()).isEqualTo(5.0);
        }
    }

    @Test
    public void configureWhenAllIsWellShouldPass() {
        // Arrange
        final String key = "first-id";
        LandModel landModel = LandModel.builder().withLandId(key).withArea(5.0).build();
        landService.create(landModel);
        Land land = landRepository.findByKey(key).orElse(null);
        assertThat(land).isNotNull();

        final LocalTime endTime = LocalTime.now(ZONE_GMT);
        final LocalTime startTime = endTime.minusMinutes(1);
        landModel = LandModel.builder().withLandModel(landModel).withWaterConfigs(
                Arrays.asList(LandModel.WaterConfig.builder().withStart(startTime)
                        .withEnd(endTime).withAmountOfWater(10L).build()
                )
        ).build();

        // Act
        landService.configure(landModel);

        // Assert
        land = landRepository.findByKey(key).orElse(null);
        assertThat(land.getWaterConfigs().size()).isEqualTo(1);
        WaterConfig waterConfig = land.getWaterConfigs().get(0);
        assertThat(waterConfig.getStart()).isEqualTo(startTime);
        assertThat(waterConfig.getEnd()).isEqualTo(endTime);
        assertThat(waterConfig.getWaterQuantity()).isEqualTo(10L);
        assertThat(land.getArea()).isEqualTo(5.0);

        // Re-arrange
        landModel = LandModel.builder().withLandModel(landModel).withWaterConfigs(
                Arrays.asList(
                        LandModel.WaterConfig.builder().withStart(startTime)
                                .withEnd(endTime).withAmountOfWater(5L).build(),
                        LandModel.WaterConfig.builder().withStart(startTime)
                                .withEnd(endTime).withAmountOfWater(7L).build()
                )
        ).build();

        // Act
        landService.configure(landModel);

        // Assert
        land = landRepository.findByKey(key).orElse(null);
        assertThat(land.getWaterConfigs().size()).isEqualTo(2);
        assertThat(land.getWaterConfigs()).filteredOn(config -> config.getWaterQuantity() == 5L).isNotEmpty();
        assertThat(land.getWaterConfigs()).filteredOn(config -> config.getWaterQuantity() == 7L).isNotEmpty();
    }

    @Test(expected = LandNotFoundException.class)
    public void updateWhenLandNotFoundShouldThrow() {
        // Arrange
        final String key = "first-id";
        LandModel landModel = LandModel.builder().withLandId(key).withArea(7.0).build();

        // Act
        landService.update(landModel);
    }

    @Test
    public void updateWhenAllIsWellShouldPass() {
        // Arrange
        final String key = "first-id";
        assertThat(landRepository.findByKey(key)).isEmpty();

        LandModel landModel = LandModel.builder().withLandId(key).withArea(5.0).build();

        landService.create(landModel);

        Land land = landRepository.findByKey(key).orElse(null);
        assertThat(land).isNotNull();
        assertThat(land.getArea()).isEqualTo(5.0);

        landModel = LandModel.builder().withLandId(key).withArea(7.0).build();

        // Act
        LandModel updatedLandModel = landService.update(landModel);

        // Assert
        land = landRepository.findByKey(key).orElse(null);
        assertThat(land).isNotNull();
        assertThat(land.getArea()).isEqualTo(7.0);

        assertThat(updatedLandModel.getArea()).isEqualTo(7.0);
        assertThat(updatedLandModel.getLandId()).isEqualTo(key);
    }

    @Test
    public void getAllShouldReturnResultsMatchingThePaginationCriteria() {
        // Arrange
        final ZoneId GMT_ZONE_ID = ZONE_GMT;
        final LocalTime endTime = LocalTime.now(GMT_ZONE_ID);
        final LocalTime startTime = endTime.minusMinutes(1);
        createEntry(LandModel.builder().withLandId("first-id").withArea(5.0).withWaterConfigs(
                Arrays.asList(LandModel.WaterConfig.builder().withStart(startTime)
                        .withEnd(endTime).withAmountOfWater(10L).build()
                )
        ).build());
        createEntry(LandModel.builder().withLandId("second-id").withArea(7.0).withWaterConfigs(
                Arrays.asList(LandModel.WaterConfig.builder().withStart(startTime)
                        .withEnd(endTime).withAmountOfWater(20L).build()
                )
        ).build());

        // Act
        PageModel<LandModel> lands = landService.getAll(0, 1);

        // Assert
        assertThat(lands.getElements()).hasSize(1);
        assertThat(lands.getElements()).filteredOn(
                elem -> elem.getLandId().equals("first-id") && elem.getArea() == 5.0
                        && elem.getWaterConfigs().get(0).getAmountOfWater() == 10L)
                .hasSize(1);

        // Act
        lands = landService.getAll(1, 1);

        // Assert
        assertThat(lands.getElements()).hasSize(1);
        assertThat(lands.getElements()).filteredOn(
                elem -> elem.getLandId().equals("second-id") && elem.getArea() == 7.0
                        && elem.getWaterConfigs().get(0).getAmountOfWater() == 20L)
                .hasSize(1);

        // Act
        lands = landService.getAll(0, 2);

        // Assert
        assertThat(lands.getElements()).hasSize(2);
        assertThat(lands.getElements()).filteredOn(
                elem -> elem.getLandId().equals("first-id") && elem.getArea() == 5.0
                        && elem.getWaterConfigs().get(0).getAmountOfWater() == 10L)
                .hasSize(1);
        assertThat(lands.getElements()).filteredOn(
                elem -> elem.getLandId().equals("second-id") && elem.getArea() == 7.0
                        && elem.getWaterConfigs().get(0).getAmountOfWater() == 20L)
                .hasSize(1);
    }

    private void createEntry(LandModel landModel) {
        landService.create(landModel);
        Land land = landRepository.findByKey(landModel.getLandId()).orElse(null);
        assertThat(land).isNotNull();
        landService.configure(landModel);
    }
}
