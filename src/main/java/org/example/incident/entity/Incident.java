/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Date;

/**
 * @author jessica.jia
 * @version : Incident.java, v 0.1 2024年11月13日 8:07 am jessica.jia Exp $
 */
@Entity
@Table(name = "incident")
public class Incident {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * What's the incident
     */
    private String name;

    /**
     * When it happened
     */
    private Date time;

    /**
     * Where it happened
     */
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}