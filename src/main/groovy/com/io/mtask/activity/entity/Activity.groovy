package com.io.mtask.activity.entity

import com.io.mtask.core.base.model.BaseEntity
import org.mongodb.morphia.annotations.Entity

@Entity
class Activity extends BaseEntity {

    Integer number

    String note

    transient String taskId

    Map changeLog = [:]
}
