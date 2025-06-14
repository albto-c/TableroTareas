import { Component, inject, input } from '@angular/core'
import { TaskDto } from '../../../Core/models/taskDto.dto'
import { TaskService } from '../../../Core/Services/taskService/task.service'
import { TaskModalService } from '../../../Core/Services/taskModalService/task-modal.service'
import { TaskComponent } from '../task/task.component'

@Component({
    selector: 'app-task-container',
    standalone: true,
    imports: [TaskComponent],
    templateUrl: './task-container.component.html',
    styleUrl: './task-container.component.css',
})
export class TaskContainerComponent {
    tasksList = input<TaskDto[]>([])
    taskStatus = input<number>(0)

    taskService = inject(TaskService)
    taskModalService = inject(TaskModalService)

    onDragOver(event: any) {
        event.preventDefault()
    }

    onDragStart(event: DragEvent, task: TaskDto) {
        event.dataTransfer?.setData('task', JSON.stringify(task))
    }

    onDropTask(event: DragEvent) {
        if (event.dataTransfer?.getData('task') != undefined) {
            let idTask = JSON.parse(event.dataTransfer?.getData('task')).id
            this.taskService.updateTaskStatus(idTask, this.taskStatus())
        }
    }

    onTaskClick(task: TaskDto) {
        this.taskModalService.openModal(task)
    }
}
