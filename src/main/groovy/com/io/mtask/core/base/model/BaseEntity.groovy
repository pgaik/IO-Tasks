package com.io.mtask.core.base.model

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.PrePersist
import org.mongodb.morphia.annotations.Property
import org.mongodb.morphia.annotations.Version

abstract class BaseEntity {

    @Id
    @Property("id")
    ObjectId id

    @Version
    @Property("version")
    Long version

    Date creationDate

    String createdBy

    Date removedDate

    String removedBy

    @PrePersist
    void beforeSave() {
        creationDate = new Date()
    }

    String getId(){
        return id.toString()
    }
}
