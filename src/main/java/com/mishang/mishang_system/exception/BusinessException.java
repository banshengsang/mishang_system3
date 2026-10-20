package com.mishang.mishang_system.exception;

/**
 * 业务校验失败时抛出，触发事务回滚
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
