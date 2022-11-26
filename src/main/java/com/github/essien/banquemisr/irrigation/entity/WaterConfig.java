package com.github.essien.banquemisr.irrigation.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Entity
@Table(name = "water_configs")
@DynamicUpdate
public class WaterConfig extends BaseAuditableModel {

    private static final long serialVersionUID = 1L;

    @Column(name = "_start", nullable = false)
    private LocalDateTime start;

    @Column(name = "_end", nullable = false)
    private LocalDateTime end;

    @Column(name = "water_quantity", nullable = false)
    private Long waterQuantity;

    public WaterConfig() {
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(Long waterQuantity) {
        this.waterQuantity = waterQuantity;
    }
}
