package com.io.mtask.sequence.entity

import com.io.mtask.core.base.model.BaseEntity
import org.mongodb.morphia.annotations.Entity

@Entity
class Sequence extends BaseEntity {

    String name

    Long number = 0
}
