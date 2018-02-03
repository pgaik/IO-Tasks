package com.io.mtask.task.dao

import com.io.mtask.activity.entity.Activity
import com.io.mtask.core.base.dao.BaseDAO
import com.io.mtask.task.dto.TaskFindData
import com.io.mtask.task.entity.Task
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore

class TaskDAO extends BaseDAO<Task> {

    TaskDAO(Datastore datastore) {
        super(datastore)
    }

    @Override
    Class getClazz() {
        Task
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

    @Override
    void update(Task task) {
        def oldTask = get(task.id)

        Activity activity
        if (oldTask.activities.size() != task.activities.size()) {
            activity = task.activities.last()
        } else {
            activity = new Activity(number: oldTask.activities.size())
            task.activities.add(activity)
        }

        oldTask.properties.each { prop, val ->
            if (prop in ['metaClass', 'class']) return
            if (task.hasProperty(prop))
                if (task[prop] != val && (task[prop] == null || task[prop] instanceof String))
                    activity.changeLog.put(prop, [oldVal: val, newVal: task[prop], type: String])
        }

        def query = datastore.find(getClazz())
                .field('_id').equal(new ObjectId(task.id))
        def op = datastore.createUpdateOperations(Task)
        task.properties.each { prop, val ->
            if (prop in ['metaClass', 'class', '_id', 'id', 'number', 'version']) return
            if (task.hasProperty(prop)) {
                if (val) {
                    op.set(prop, val)
                } else {
                    op.unset(prop)
                }
            }
        }
        def results = datastore.findAndModify(query, op)
        if (!results) {
            throw new Exception()
        }
    }
}
