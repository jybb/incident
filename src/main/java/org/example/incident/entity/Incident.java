/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
     * 事件名称
     */
    private String name;

    /**
     * 发生的时间
     */
    private Date happenTime;

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

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }
}