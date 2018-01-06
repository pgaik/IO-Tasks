package com.io.mtask.task.entity

/**
 * Created by Paweł on 2018-01-06.
 */
class TaskOptionResponse {
    static getResponse(){
        ['GET': [
                'description': 'Find tasks',
                'parameters' : [
                        'number'     : [
                                'type'       : 'number',
                                'description': 'Task number'
                        ],
                        'name'       : [
                                'type'       : 'string',
                                'description': 'Task name'
                        ],
                        'description': [
                                'type'       : 'string',
                                'description': 'Task name'
                        ],
                        'createdBy'  : [
                                'type'       : 'string',
                                'description': 'Task name'
                        ],
                        'assigned'   : [
                                'type'       : 'string',
                                'description': 'Task name'
                        ],
                        'example'    : [
                                'request' : ['number': 1],
                                'response': []
                        ]

                ]]]
    }
}
