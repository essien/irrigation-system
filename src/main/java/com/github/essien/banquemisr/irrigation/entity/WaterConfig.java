package com.github.essien.banquemisr.irrigation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Entity representing water configuration.
 *
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Entity
@Table(name = "water_configs")
@DynamicUpdate
public class WaterConfig extends BaseAuditableModel {

    private static final long serialVersionUID = 1L;

    /**
     * Cron expression describing when to execute.
     */
    @Column(name = "cron", nullable = false, length = 127)
    private String cron;

    /**
     * Describes how long to run (in minutes).
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "water_quantity", nullable = false)
    private Long waterQuantity;

    public WaterConfig() {
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(Long waterQuantity) {
        this.waterQuantity = waterQuantity;
    }
}
