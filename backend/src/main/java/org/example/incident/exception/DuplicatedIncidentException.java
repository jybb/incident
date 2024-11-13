/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.exception;

/**
 * @author jessica.jia
 * @version : DuplicatedIncidentException.java, v 0.1 2024年11月13日 4:39 pm jessica.jia Exp $
 */
public class DuplicatedIncidentException extends RuntimeException {
    public DuplicatedIncidentException(String message) {
        super(message);
    }
}