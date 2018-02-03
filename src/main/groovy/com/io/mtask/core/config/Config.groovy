package com.io.mtask.core.config

import com.io.mtask.core.database.DataSource
import groovy.json.JsonSlurper

class Config {
    private static Config INSTANCE = null

    DataSource dataSource = null

    private Config() {
    }

    private init(){
        File file = new File('/home/dusio/Desktop/IO-Tasks/src/ratpack/config.json')
        def config = new JsonSlurper().parse(file).dataSource as Map
        dataSource = new DataSource(host: config.host, port: config.port,
                database: config.database, username: config.username,
                password: config.password, source: config.source, noAuthorization:  config.noAuthorization)
    }

    static Config getInstance() {
        if (!INSTANCE) {
            INSTANCE = new Config()
            INSTANCE.init()
        }
        return INSTANCE
    }
}
