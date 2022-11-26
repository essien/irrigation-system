package com.github.essien.banquemisr.irrigation.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Entity
@Table(name = "lands")
@DynamicUpdate
public class Land extends BaseAuditableModel {

    private static final long serialVersionUID = 1L;

    @Column(name = "_key", unique = true, nullable = false, length = 127)
    private String key;

    @Column(name = "area")
    private Double area;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "lands_x_water_configs", joinColumns = @JoinColumn(name = "land_id", nullable = false),
               inverseJoinColumns = @JoinColumn(name = "water_config_id", nullable = false))
    private List<WaterConfig> waterConfigs;

    public Land() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<WaterConfig> getWaterConfigs() {
        return waterConfigs;
    }

    public void setWaterConfigs(List<WaterConfig> waterConfigs) {
        this.waterConfigs = waterConfigs;
    }
}
