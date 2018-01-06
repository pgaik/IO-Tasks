package com.io.mtask.core.handler.error

import com.io.mtask.core.exeption.ServerException
import groovy.util.logging.Slf4j
import ratpack.error.ServerErrorHandler
import ratpack.handling.Context

/**
 * Created by Paweł on 2018-01-06.
 */
@Slf4j
class SysErrorHandler implements ServerErrorHandler {
    @Override
    void error(Context context, Throwable throwable) {
        log.warn 'Errors', throwable
        context.with {
            response.status((throwable as ServerException).code).send()
        }
    }
}
