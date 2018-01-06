package com.io.mtask.core.base.dao

import com.io.mtask.activity.dto.BaseFindData
import com.io.mtask.core.base.model.BaseEntity
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore

/**
 * Created by Paweł on 2018-01-06.
 */
abstract class BaseDAO<T extends BaseEntity> {

    Datastore datastore

    BaseDAO(Datastore datastore) {
        this.datastore = datastore
    }

    T get(ObjectId objectId) {
        datastore.get(T, objectId)
    }

    ObjectId insert(T task) {
        datastore.save(task).id as ObjectId
    }

    void remove(T t) {
        remove(new ObjectId(t.id))
    }

    void remove(ObjectId id) {
        def query = datastore.createQuery(T).field('id').equal(id)
        def ops = datastore.createUpdateOperations(T).set('removedDate', new Date())
        datastore.updateFirst(query, ops)
    }

}
