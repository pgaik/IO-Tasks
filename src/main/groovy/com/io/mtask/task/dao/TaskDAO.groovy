package com.io.mtask.task.dao

import com.io.mtask.core.base.dao.BaseDAO
import com.io.mtask.task.dto.TaskFindData
import com.io.mtask.task.entity.Task
import org.mongodb.morphia.Datastore

class TaskDAO extends BaseDAO<Task> {

    TaskDAO(Datastore datastore) {
        super(datastore)
    }

    List<Task> find(TaskFindData findData) {
        def find = datastore.find(Task).field('removedDate').equal(null)
        if (findData.number) {
            find.field('number').equal(findData.number)
        }
        if (findData.name) {
            find.field('name').containsIgnoreCase(findData.name)
        }
        if (findData.description) {
            find.field('description').containsIgnoreCase(findData.description)
        }
        if (findData.createdBy) {
            find.field('createdBy').equal(findData.createdBy)
        }
        if (findData.assigned) {
            find.field('assigned').equal(findData.assigned)
        }
        if (findData.createdBy) {
            find.field('createdBy').equal(findData.createdBy)
        }
        if (findData.status) {
            find.field('status').equal(findData.status)
        }
        return find.asList()
    }

}
