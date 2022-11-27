package com.github.essien.banquemisr.irrigation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Configuration
@EnableJpaAuditing
@EnableScheduling
public class SystemConfig {
}
