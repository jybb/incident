/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.exception;

/**
 * @author jessica.jia
 * @version : ClientRequestException.java, v 0.1 2024年11月13日 11:02 pm jessica.jia Exp $
 */
public class ClientRequestException extends RuntimeException {
    public ClientRequestException() {
    }

    public ClientRequestException(String message) {
        super(message);
    }
}