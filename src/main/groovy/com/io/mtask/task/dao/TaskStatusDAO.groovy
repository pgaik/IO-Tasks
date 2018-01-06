package com.io.mtask.task.dao

import com.io.mtask.core.base.dao.BaseDAO
import com.io.mtask.task.dto.TaskStatusFindData
import com.io.mtask.task.entity.TaskStatus
import org.mongodb.morphia.Datastore

class TaskStatusDAO extends BaseDAO<TaskStatus> {

    TaskStatusDAO(Datastore datastore) {
        super(datastore)
    }

    @Override
    Class getClazz() {
        TaskStatus
    }

    List<TaskStatus> find(TaskStatusFindData findData){
        datastore.find(TaskStatus).field('code').equal(findData.code).asList()
    }
}
