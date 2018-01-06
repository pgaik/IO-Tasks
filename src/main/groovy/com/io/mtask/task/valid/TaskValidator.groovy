package com.io.mtask.task.valid

import com.io.mtask.core.valid.Validator
import com.io.mtask.task.dao.TaskStatusDAO
import com.io.mtask.task.dto.TaskStatusFindData
import com.io.mtask.task.entity.Task

class TaskValidator extends Validator<Task> {

    private TaskStatusDAO statusDAO

    TaskValidator(TaskStatusDAO statusDAO) {
        this.statusDAO = statusDAO
    }

    Task validate(Task task) {
        if (!task.name) {
            throw new Exception()
        }
        if (!task.createdBy) {
            throw new Exception()
        }

        if (statusDAO.find(new TaskStatusFindData(code: task.status)).isEmpty()) {
            throw new Exception()
        }

        task
    }

    static TaskValidator validator(TaskStatusDAO statusDAO) {
        return new TaskValidator(statusDAO)
    }
}
