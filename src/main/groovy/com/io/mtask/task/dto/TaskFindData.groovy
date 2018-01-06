package com.io.mtask.task.dto

import com.io.mtask.activity.dto.BaseFindData

class TaskFindData extends BaseFindData {
    Long number
    String name
    String description
    String status
    String createdBy
    String assigned
}
