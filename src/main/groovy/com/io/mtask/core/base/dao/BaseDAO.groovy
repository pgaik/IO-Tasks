package com.io.mtask.core.base.dao

import com.io.mtask.core.base.model.BaseEntity
import com.io.mtask.core.exeption.NotFoundException
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
    T get(String objectId) {
    get(new ObjectId(objectId))
    }

    T get(ObjectId objectId) {
        def result = datastore.find(getClazz())
                .field('_id').equal(objectId)
                .field('removedDate').equal(null).get()
        if (!result) {
            throw new NotFoundException()
        }
        result as T
    }

    ObjectId insert(T t) {
        datastore.save(t).id as ObjectId
    }

    void update(T t) {
        def query = datastore.find(getClazz())
                .field('_id').equal(t.id)
                .field('removedDate').equal(null)
        def results = datastore.updateFirst(query, t, false)
        if (results.updatedCount == 0) {
            throw new Exception()
        }
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
