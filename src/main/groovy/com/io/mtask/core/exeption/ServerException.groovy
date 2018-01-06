package com.io.mtask.core.exeption

/**
 * Created by Paweł on 2018-01-06.
 */
class ServerException extends Exception {

    ServerException(Integer code) {
        this.code = code
    }

    private Integer code

    Integer getCode() {
        return code
    }

}
