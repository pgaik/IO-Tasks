package com.io.mtask.task.entity

import com.io.mtask.core.base.model.BaseEntity
import org.mongodb.morphia.annotations.Entity

/**
 * Created by Paweł on 2018-01-06.
 */
@Entity
class TaskStatus extends BaseEntity {

    String code

    Map<String, String> name = [:]

    Boolean closed = false

}
