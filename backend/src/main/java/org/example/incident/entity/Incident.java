/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
    @Positive
    private Long id;

    /**
     * What's the incident
     */
    @Column(unique = true)
    @NotBlank(message = "name is required")
    @Size(max = 30, message = "max length of name is 30")
    private String name;

    /**
     * When it happened
     */
    private Date time;

    /**
     * Where it happened
     */
    @Size(max = 100, message ="max length of address is 100")
    private String address;

    /**
     * When count < 0, incident cannot be updated
     */
    private Integer updateCount;

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

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", address='" + address + '\'' +
                ", updateCount=" + updateCount +
                '}';
    }
}