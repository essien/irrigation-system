package com.github.essien.banquemisr.irrigation.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author bodmas
 * @since Nov 27, 2022.
 */
@Entity
@Table(name = "irrigation_executions")
@DynamicUpdate
public class IrrigationExecution extends BaseAuditableModel {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "land_id", nullable = false)
    private Land land;

    @Column(name = "irrigation_time", nullable = false)
    private LocalDateTime irrigationTime;

    public IrrigationExecution() {
    }

    public IrrigationExecution(Land land, LocalDateTime irrigationTime) {
        this.land = land;
        this.irrigationTime = irrigationTime;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public LocalDateTime getIrrigationTime() {
        return irrigationTime;
    }

    public void setIrrigationTime(LocalDateTime irrigationTime) {
        this.irrigationTime = irrigationTime;
    }
}
