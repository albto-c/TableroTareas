import { Injectable, signal } from '@angular/core'
import { TaskDto } from '../../models/taskDto.dto'
import { Subject } from 'rxjs'

@Injectable({
    providedIn: 'root',
})
export class TaskModalService {
    constructor() {}

    showModal = signal(false)
    taskSelected$ = new Subject<TaskDto | undefined>()

    openModal(task?: TaskDto) {
        this.taskSelected$.next(task)
        this.showModal.set(true)
    }
    closeModal() {
        this.taskSelected$.next(undefined)
        this.showModal.set(false)
    }
}
