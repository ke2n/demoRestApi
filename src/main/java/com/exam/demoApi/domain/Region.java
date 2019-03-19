package com.exam.demoApi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "REGION")
@NoArgsConstructor
public class Region {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "sequence_region_id", strategy = "com.exam.demoApi.common.RegionIdGenerator")
    @GeneratedValue(generator = "sequence_region_id")
    private String regionCode;

    private String regionName;

    public Region(String regionName) {
        this.regionName = regionName;
    }
}
