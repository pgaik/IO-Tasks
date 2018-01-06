package com.io.mtask.core.exeption

/**
 * Created by Paweł on 2018-01-06.
 */
class NotFoundException extends ServerException {

    NotFoundException() {
        super(404)
    }
}
