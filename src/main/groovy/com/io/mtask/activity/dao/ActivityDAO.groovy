package com.io.mtask.activity.dao

import com.io.mtask.activity.entity.Activity
import com.io.mtask.core.base.dao.BaseDAO
import org.mongodb.morphia.Datastore

class ActivityDAO extends BaseDAO<Activity> {

    ActivityDAO(Datastore datastore) {
        super(datastore)
    }

    @Override
    Class getClazz() {
        Activity
    }
}
