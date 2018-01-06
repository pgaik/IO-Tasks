package com.io.mtask.core.database

import com.google.inject.AbstractModule
import com.io.mtask.sequence.dao.SequenceDAO
import com.io.mtask.task.dao.TaskDAO
import com.io.mtask.task.dao.TaskStatusDAO
import com.io.mtask.task.valid.TaskValidator
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia

class MongoModule extends AbstractModule {

    private Datastore _datastore

    private DataSource dataSource

    MongoModule(DataSource dataSource) {
        this.dataSource = dataSource
    }

    @Override
    protected void configure() {
        def taskDAO = new TaskDAO(datastore)
        def tasStatusDAO = new TaskStatusDAO(datastore)
        bind(TaskDAO).toInstance(taskDAO)
        bind(SequenceDAO).toInstance(new SequenceDAO(datastore))
        bind(TaskStatusDAO).toInstance(tasStatusDAO)

        bind(TaskValidator).toInstance(TaskValidator.validator(tasStatusDAO))
    }

    private static MongoClient getMongoClient(DataSource dataSource) {

        MongoCredential mongoCredential = null
        MongoClient mongoClient
        if (!dataSource.noAuthorization) {
            mongoCredential = MongoCredential.createScramSha1Credential(dataSource.username, dataSource.source)
        }
        def serverAddress = new ServerAddress(dataSource.host, dataSource.port)
        if (!mongoCredential) {
            mongoClient = new MongoClient(serverAddress)
        } else {
            mongoClient = new MongoClient(serverAddress, mongoCredential, new MongoClientOptions.Builder().build())
        }
        mongoClient
    }


    Datastore getDatastore() {
        if (!_datastore) {
            _datastore = new Morphia().createDatastore(getMongoClient(dataSource), dataSource.database)
        }
        return _datastore
    }
}
