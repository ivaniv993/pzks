package com.luxoft.entity.model;

import com.luxoft.entity.model.enumeration.EtlStatus;

import javax.persistence.*;

/**
 * Created by iivaniv on 18.03.2016.
 *
 */
//@Entity
//@Table(name = "etl", schema = "public")
public class Etl {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private Long id;


//    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
    private EtlStatus etlStatus;
}