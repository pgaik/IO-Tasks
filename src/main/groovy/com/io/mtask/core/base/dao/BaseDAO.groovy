package com.io.mtask.core.base.dao

import com.io.mtask.core.base.model.BaseEntity
import com.io.mtask.task.entity.Task
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

    abstract Class getClazz()

    T get(ObjectId objectId) {
        def result = datastore.find(getClazz())
                .field('_id').equal(objectId)
                .field('removedDate').equal(null).get()
        if (!result) {
            throw new Exception('NOT FOUND')
        }
        result as T
    }

    ObjectId insert(T task) {
        datastore.save(task).id as ObjectId
    }

    void remove(T t) {
        remove(new ObjectId(t.id))
    }

    void remove(ObjectId id) {
        def query = datastore.createQuery(getClazz()).field('id').equal(id)
        def ops = datastore.createUpdateOperations(getClazz()).set('removedDate', new Date())
        datastore.updateFirst(query, ops)
    }

}
