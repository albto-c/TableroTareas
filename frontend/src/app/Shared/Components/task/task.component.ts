import { Component, inject, input } from '@angular/core'
import { TaskDto } from '../../../Core/models/taskDto.dto'
import { TaskService } from '../../../Core/Services/taskService/task.service'

@Component({
    selector: 'app-task',
    standalone: true,
    imports: [],
    templateUrl: './task.component.html',
    styleUrl: './task.component.css',
})
export class TaskComponent {
    task = input<TaskDto>({
        id: 0,
        title: '',
        description: '',
        date: '',
        taskStatus: 0,
    })

    taskService = inject(TaskService)
}
