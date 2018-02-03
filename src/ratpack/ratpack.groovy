package ratpack

import com.io.mtask.activity.dao.ActivityDAO
import com.io.mtask.activity.entity.Activity
import com.io.mtask.core.config.Config
import com.io.mtask.core.database.DataSource
import com.io.mtask.core.database.MongoModule
import com.io.mtask.core.handler.error.SysErrorHandler
import com.io.mtask.sequence.dao.SequenceDAO
import com.io.mtask.task.dao.TaskDAO
import com.io.mtask.task.dao.TaskStatusDAO
import com.io.mtask.task.dto.TaskFindData
import com.io.mtask.task.entity.Task
import com.io.mtask.task.constant.TaskOptionResponse
import com.io.mtask.task.entity.TaskStatus
import com.io.mtask.task.valid.TaskValidator
import groovy.json.JsonSlurper
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.error.ServerErrorHandler
import ratpack.exec.Promise
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json

final Logger logger = LoggerFactory.getLogger(ratpack.class)

ratpack {
    serverConfig {
        port(9000)
        Config.getInstance()
    }
    bindings {
        module new MongoModule(new DataSource(new JsonSlurper().parse(new File('config/config.json')).dataSource as Map))

        bind ServerErrorHandler, SysErrorHandler
    }

    handlers {
        all RequestLogger.ncsa(logger)
        all(RequestLogger.of { outcome ->
            if (RequestLogger.LOGGER.infoEnabled) {
                RequestLogger.LOGGER.info(
                        'Request for {} took {}.{} seconds.',
                        outcome.request.uri,
                        outcome.duration.seconds,
                        outcome.duration.nano)
            }

        })

        get {
            render "Tasks!"
        }
        path('task') {
            byMethod {
                options { TaskDAO taskDAO ->
                    header('Allow', 'HEAD,GET,POST,DELETE,OPTIONS')
                    render(json(TaskOptionResponse.getResponse()))
                }
                get { TaskDAO taskDAO ->
                    Promise.value(request.queryParams).map() { map ->
                        def findData = new TaskFindData()
                        findData.number = map.number != null ? Long.valueOf(map.number) : null
                        findData.name = map.name
                        findData.description = map.description
                        findData.createdBy = map.createdBy
                        findData
                    }.onError() { findData ->
                        response.status(400).send()
                    }.then { findData ->
                        render(json(taskDAO.find(findData)))
                    }
                }

                post { TaskDAO taskDAO, SequenceDAO sequence, TaskValidator validator ->
                    parse(fromJson(Task)).map { Task task ->
                        validator.validate(task)
                        task.number = sequence.getNext('task')
                        task
                    }.onError {
                        response.status(400).send()
                    }.then { Task task ->
                        taskDAO.insert(task)
                        response.status(200).send()
                    }
                }
            }
        }
        path('task/:id') {
            byMethod {
                get { TaskDAO taskDAO ->
                    Promise.value(pathTokens).map() { tokens ->
                        new ObjectId(tokens.id)
                    }.onError() { findData ->
                        response.status(400).send()
                    }.then { id ->
                        render(json(taskDAO.get(id)))
                    }
                }
                delete { TaskDAO taskDAO ->
                    Promise.value(pathTokens).map() { tokens ->
                        new ObjectId(tokens.id)
                    }.onError() { findData ->

                    }.then { id ->
                        taskDAO.remove(new ObjectId(pathTokens.id))
                        response.status(200).send()
                    }
                }
            }
        }
        post('activity') { ActivityDAO activityDAO, SequenceDAO sequence ->
            parse(fromJson(Activity)).map { Activity activity ->
                activity.number = sequence.getNext('activity')
                activity
            }.onError {
                response.status(400).send()
            }.then { Activity activity ->
                activityDAO.insert(activity)
                response.status(200).send()
            }
        }
        post('taskStatus') { TaskStatusDAO statusDAO ->
            parse(fromJson(TaskStatus)).map { TaskStatus status ->
                status
            }.onError {
                response.status(400).send()
            }.then { TaskStatus status ->
                statusDAO.insert(status)
                response.status(200).send()
            }
        }
    }
}
