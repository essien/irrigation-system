package com.github.essien.banquemisr.irrigation.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import static com.github.essien.banquemisr.irrigation.Constants.ZONE_GMT;
import com.github.essien.banquemisr.irrigation.config.MapperFactory;
import com.github.essien.banquemisr.irrigation.dto.LandConfigurationDto;
import com.github.essien.banquemisr.irrigation.dto.LandCreationDto;
import com.github.essien.banquemisr.irrigation.dto.LandModificationDto;
import com.github.essien.banquemisr.irrigation.dto.WaterConfigDto;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import com.github.essien.banquemisr.irrigation.service.LandService;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Tests for {@link LandResource}.
 *
 * @author bodmas
 * @since Nov 25, 2022.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LandResource.class)
@Import(MapperFactory.class)
public class LandResourceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private LandService landService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void testAddWhenLandIdIsMissing() throws Exception {
        LandCreationDto landCreationDto = LandCreationDto.builder().build();
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/lands").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(landCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("'landId' must be supplied"));
    }

    @Test
    public void testAddWhenAllRequiredFieldsArePresent() throws Exception {
        LandCreationDto landCreationDto = LandCreationDto.builder().withLandId("first-id").build();
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/lands").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(landCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Okay, land was successfully created."))
                .andExpect(MockMvcResultMatchers.jsonPath("data.landId").value("first-id"));
    }

    @Test
    public void testConfigureWhenLandIdIsSetInRequestBody() throws Exception {
        final Integer duration = 15;
        final String cron = "0/5 * * ? * * *";
        LandConfigurationDto landConfigurationDto = LandConfigurationDto.builder().withLandId("something").withWaterConfigs(
                Arrays.asList(WaterConfigDto.builder().withCron(cron)
                        .withDuration(duration).withAmountOfWater(10L).build()
                )
        ).build();
        final String writeValueAsString = objectMapper.writeValueAsString(landConfigurationDto);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/lands/land-id/configure").contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("'landId' should only be set in URI"));
    }

    @Test
    public void testConfigureWhenAllRequiredFieldsArePresent() throws Exception {
        final Integer duration = 60;
        final String cron = "0/5 * * ? * * *";
        LandConfigurationDto landConfigurationDto = LandConfigurationDto.builder().withWaterConfigs(
                Arrays.asList(WaterConfigDto.builder().withCron(cron)
                        .withDuration(duration).withAmountOfWater(10L).build()
                )
        ).build();
        final String writeValueAsString = objectMapper.writeValueAsString(landConfigurationDto);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/lands/land-id/configure").contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Okay, land configuration was successfully updated."))
                .andExpect(MockMvcResultMatchers.jsonPath("data.landId").value("land-id"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.waterConfigs[0].cron").value(cron))
                .andExpect(MockMvcResultMatchers.jsonPath("data.waterConfigs[0].duration").value(duration))
                .andExpect(MockMvcResultMatchers.jsonPath("data.waterConfigs[0].amountOfWater").value(10L));
    }

    @Test
    public void testEditWhenSomeRequiredFieldsAreMissing() throws Exception {
        LandModificationDto landModificationDto = LandModificationDto.builder().build();
        BDDMockito.willAnswer(iom -> iom.getArgument(0)).given(landService).update(any(LandModel.class));

        final String writeValueAsString = objectMapper.writeValueAsString(landModificationDto);
        mvc.perform(MockMvcRequestBuilders.patch("/api/v1/lands/land-id").contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("'area' must be supplied"));
    }

    @Test
    public void testEditWhenAllRequiredFieldsArePresent() throws Exception {
        LandModificationDto landModificationDto = LandModificationDto.builder().withArea(20.0).build();
        BDDMockito.willAnswer(iom -> iom.getArgument(0)).given(landService).update(any(LandModel.class));

        final String writeValueAsString = objectMapper.writeValueAsString(landModificationDto);
        mvc.perform(MockMvcRequestBuilders.patch("/api/v1/lands/land-id").contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Okay, land was successfully updated."))
                .andExpect(MockMvcResultMatchers.jsonPath("data.landId").value("land-id"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.area").value(20.0));
    }

    @Test
    public void testGetPaginated() throws Exception {
        final Integer duration = 15;
        final String cron = "0/5 * * ? * * *";
        BDDMockito.given(landService.getAll(any(Integer.class), any(Integer.class))).willReturn(
                new PageModel<>(Arrays.asList(
                        LandModel.builder().withLandId("first").withArea(5.0).withWaterConfigs(
                                Arrays.asList(LandModel.WaterConfig.builder().withCron(cron)
                                        .withDuration(duration).withAmountOfWater(10L).build())
                        ).build(),
                        LandModel.builder().withLandId("second").withArea(10.0).withWaterConfigs(
                                Arrays.asList(LandModel.WaterConfig.builder().withCron(cron)
                                        .withDuration(duration).withAmountOfWater(20L).build())
                        ).build()
                ), 0, 5)
        );

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/lands"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Okay, 2 lands found."));
    }
}
