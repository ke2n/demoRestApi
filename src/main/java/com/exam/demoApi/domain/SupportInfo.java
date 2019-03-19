package com.exam.demoApi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SUPPORT_INFO")
public class SupportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private int supportId;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private Region region;

    private String target;

    private String usage;

    @Column(name = "limit_str")
    private String limit;

    @Column(name = "limit_num")
    private long limitNum;

    private String rate;

    private Double avgRate;

    private Double minRate;

    private String institute;

    private String mgmt;

    private String reception;
}
