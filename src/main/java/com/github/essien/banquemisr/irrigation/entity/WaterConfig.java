package com.github.essien.banquemisr.irrigation.entity;

import java.time.LocalTime;
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

    @Column(name = "_start", nullable = false)
    private LocalTime start;

    @Column(name = "_end", nullable = false)
    private LocalTime end;

    @Column(name = "water_quantity", nullable = false)
    private Long waterQuantity;

    public WaterConfig() {
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Long getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(Long waterQuantity) {
        this.waterQuantity = waterQuantity;
    }
}
