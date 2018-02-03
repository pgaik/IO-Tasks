package com.io.mtask.task.constant

import com.io.mtask.core.config.Config

/**
 * Created by Paweł on 2018-01-06.
 */
class TaskOptionResponse {
    static getResponse() {
        ['GET'   : [
                get   : [
                        description : 'Get task by id',
                        'parameters': [
                                'id': [
                                        'type'       : 'String',
                                        'description': 'Id',
                                        mandatory    : true
                                ]],
                        'example'   : [
                                'request' : "${Config.getInstance().getBaseUrl()}/status/5a74ed710000000000000000".toString(),
                                'response': ['number': 1, 'name': 'Task name', description: 'This is description', status: 'N', createdBy: 'pkowalski', assigned: 'aflorek', watchers: ['boss1', 'boss3']]
                        ]
                ],
                'find': [
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
                                        'description': 'Description'
                                ],
                                'status'     : [
                                        'type'         : 'string',
                                        'description'  : 'Status code',
                                        'statusDetails': "${Config.getInstance().getBaseUrl()}/statusDetails".toString()
                                ],
                                'createdBy'  : [
                                        'type'       : 'string',
                                        'description': 'Created By'
                                ],
                                'assigned'   : [
                                        'type'       : 'string',
                                        'description': 'assigned to'
                                ],
                                'watchers'   : [
                                        'type'       : 'list of strings',
                                        'description': 'watchers'
                                ]],
                        'example'    : [
                                'request' : "${Config.getInstance().getBaseUrl()}/status/5a74ed710000000000000000",
                                'response': [['number': 1, 'name': 'Task name', description: 'This is description', status: 'N', createdBy: 'pkowalski', assigned: 'aflorek', watchers: ['boss1', 'boss3']]]
                        ]]

        ],
         'DELETE': [
                 description : 'Delete task by id',
                 'parameters': [
                         'id': [
                                 'type'       : 'String',
                                 'description': 'Id',
                                 mandatory    : true
                         ]],
                 'example'   : [
                         'request' : "${Config.getInstance().getBaseUrl()}/5a74ed710000000000000000",
                         'response': [code: 200]
                 ]
         ],
         'POST'  : [
                 'description': 'Create task',
                 'parameters' : [
                         'name'       : [
                                 'type'       : 'string',
                                 'description': 'Task name',
                                 'mandatory'  : true
                         ],
                         'description': [
                                 'type'       : 'string',
                                 'description': 'Description'
                         ],
                         'status'     : [
                                 'type'         : 'string',
                                 'description'  : 'Status code',
                                 'statusDetails': "${Config.getInstance().getBaseUrl()}/statusDetails".toString(),
                                 'mandatory'    : true
                         ],
                         'createdBy'  : [
                                 'type'       : 'string',
                                 'description': 'Created By'
                         ],
                         'assigned'   : [
                                 'type'       : 'string',
                                 'description': 'assigned to'
                         ],
                         'watchers'   : [
                                 'type'       : 'list of strings',
                                 'description': 'watchers'
                         ]
                 ],
                 'example'    : [
                         'request' : ['number': 1, 'name': 'Task name', description: 'This is description', status: 'N', createdBy: 'pkowalski', assigned: 'aflorek', watchers: ['boss1', 'boss3']],
                         'response': [code: 200]
                 ]

         ]]
    }
}
