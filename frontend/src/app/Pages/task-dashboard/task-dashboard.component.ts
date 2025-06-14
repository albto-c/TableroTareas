import { Component, inject, input, signal } from '@angular/core'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { CommonModule } from '@angular/common'
import { TaskContainerComponent } from '../../Shared/Components/task-container/task-container.component'
import { TaskModalComponent } from '../../Shared/Components/task-modal/task-modal.component'
import { TaskModalService } from '../../Core/Services/taskModalService/task-modal.service'
import { TaskService } from '../../Core/Services/taskService/task.service'
import { RouterLink } from '@angular/router'
import { CalendarService } from '../../Core/Services/calendarService/calendar.service'
import { TaskDto } from '../../Core/models/taskDto.dto'
import { MatProgressSpinner } from '@angular/material/progress-spinner'
import { catchError, EMPTY, Subject, takeUntil } from 'rxjs'

@Component({
    selector: 'app-task-dashboard',
    standalone: true,
    imports: [
        MatFormFieldModule,
        MatInputModule,
        CommonModule,
        TaskContainerComponent,
        TaskModalComponent,
        RouterLink,
        MatProgressSpinner
    ],
    templateUrl: './task-dashboard.component.html',
    styleUrl: './task-dashboard.component.css',
})
export class TaskDashBoardComponent {
    taskModalService = inject(TaskModalService)
    taskService = inject(TaskService)
    calendarService = inject(CalendarService)

    isTodoSelected = signal(true)
    isFinishedSelected = signal(false)
    loadComponent = signal(false)

    date = input<string>('') // Route param

    day: number = 0
    month: number = 0
    year: number = 0

    allTasks: TaskDto[] = []
    tasksToDo: TaskDto[] = []
    tasksFinished: TaskDto[] = []
    private destroy$ = new Subject<void>()

    ngOnInit() {
        this.setDateValues(this.date())
        this.taskService
            .getTaskByDate(this.calendarService.getDate())
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.allTasks = []
                    this.refillAllTasksList()
                    return EMPTY
                })
            )
            .subscribe(res => {
                this.allTasks =
                    res != null && res.tasksList != null ? res.tasksList : []
                this.refillAllTasksList()
            })

        this.taskService
            .getTasksList()
            .pipe(takeUntil(this.destroy$))
            .subscribe(res => {
                this.allTasks = [...res]
                this.refillAllTasksList()
            })
        this.loadComponent.set(true)
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }

    setDateValues(dateParam: string) {
        let day, month, year
        if (dateParam != null) {
            day = parseInt(
                dateParam.substring(
                    dateParam.indexOf('d') + 1,
                    dateParam.indexOf('m')
                )
            )
            month =
                parseInt(
                    dateParam.substring(
                        dateParam.indexOf('m') + 1,
                        dateParam.indexOf('y')
                    )
                ) + 1
            year = parseInt(dateParam.substring(dateParam.indexOf('y') + 1))
        }

        if (day != null && month != null && year != null) {
            this.day = day
            this.month = month
            this.year = year
        }
    }

    refillAllTasksList() {
        this.tasksToDo.length = 0
        this.tasksFinished.length = 0
        if (this.allTasks.length > 0) {
            this.allTasks.forEach(task =>
                task.taskStatus == 1
                    ? this.tasksToDo.push(task)
                    : this.tasksFinished.push(task)
            )
        }
    }
}
