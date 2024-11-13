/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.exception;

/**
 * @author jessica.jia
 * @version : IncidentNotFoundException.java, v 0.1 2024年11月13日 4:56 pm jessica.jia Exp $
 */
public class IncidentNotFoundException extends ClientRequestException {
    public IncidentNotFoundException(String message) {
        super(message);
    }
}