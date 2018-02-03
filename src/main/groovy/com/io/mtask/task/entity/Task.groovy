package com.io.mtask.task.entity

import com.io.mtask.core.base.model.BaseEntity
import com.io.mtask.activity.entity.Activity
import com.io.mtask.core.database.MongoModule
import com.io.mtask.sequence.dao.SequenceDAO
import jdk.nashorn.internal.ir.annotations.Reference
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.PrePersist

@Entity
class Task extends BaseEntity {

    String name

    Long number

    String description

    String assigned

    String status

    List<String> watchers = []

    List<Activity> activities = []

}
