package com.io.mtask.core.config

import com.io.mtask.core.database.DataSource
import groovy.json.JsonSlurper

class Config {
    private static Config INSTANCE = null

    DataSource dataSource = null
    private String baseUrl

    private Config() {
    }

    private init() {
        File file = new File('config/config.json')
        def config = new JsonSlurper().parse(file) as Map
        dataSource = new DataSource(host: config.dataSource.host, port: config.dataSource.port,
                database: config.dataSource.database, username: config.dataSource.username,
                password: config.dataSource.password, source: config.dataSource.source, noAuthorization: config.dataSource.noAuthorization)
        baseUrl = config.baseUrl
    }

    static Config getInstance() {
        if (!INSTANCE) {
            INSTANCE = new Config()
            INSTANCE.init()
        }
        return INSTANCE
    }

    String getBaseUrl() {
        return baseUrl
    }
}
