package com.github.essien.banquemisr.irrigation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Configuration
@Import({MapperFactory.class, PersistenceConfig.class})
public class TestConfig {
}
