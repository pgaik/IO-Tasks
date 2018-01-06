package com.io.mtask.sequence.dao

import com.io.mtask.sequence.entity.Sequence
import org.mongodb.morphia.Datastore

class SequenceDAO {

    Datastore datastore

    SequenceDAO(Datastore datastore) {
        this.datastore = datastore
    }

    Long getNext(String name) {
        datastore.findAndModify(datastore.find(Sequence).field('name').equal(name), datastore.createUpdateOperations(Sequence).inc("number", 1), false, true).number
    }
}
