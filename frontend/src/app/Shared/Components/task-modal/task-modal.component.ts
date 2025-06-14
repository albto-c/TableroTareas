import { Component, inject, signal } from '@angular/core'
import { TaskModalService } from '../../../Core/Services/taskModalService/task-modal.service'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule,
} from '@angular/forms'
import { AlertService } from '../../../Core/Services/alertService/alert.service'
import { AlertType } from '../../../Core/Enums/alertType.enum'
import { CalendarService } from '../../../Core/Services/calendarService/calendar.service'
import { TaskService } from '../../../Core/Services/taskService/task.service'
import { TaskCDto } from '../../../Core/models/taskCDto.dto'
import { Subject, takeUntil } from 'rxjs'

@Component({
    selector: 'app-task-modal',
    standalone: true,
    imports: [
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
    ],
    templateUrl: './task-modal.component.html',
    styleUrl: './task-modal.component.css',
})
export class TaskModalComponent {
    taskModalService = inject(TaskModalService)
    alertService = inject(AlertService)
    calendarService = inject(CalendarService)
    taskService = inject(TaskService)

    private destroy$ = new Subject<void>()

    showButtonCreate = signal(true)

    formCreateTask: FormGroup = new FormGroup({
        title: new FormControl('', [Validators.required]),
        description: new FormControl(''),
    })

    ngOnInit() {
        this.taskModalService.taskSelected$
            .pipe(takeUntil(this.destroy$))
            .subscribe(res => {
                if (res != undefined) {
                    this.formCreateTask.setValue({
                        title: res.title,
                        description: res.description,
                    })
                    this.formCreateTask.disable()
                    this.showButtonCreate.set(false)
                } else {
                    this.formCreateTask.reset()
                    this.formCreateTask.enable()
                    this.showButtonCreate.set(true)
                }
            })
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }

    onSubmit() {
        if (this.formCreateTask.valid) {
            var task: TaskCDto = {
                title: this.formCreateTask.get('title')?.value,
                description: this.formCreateTask.get('description')?.value,
                date: this.calendarService.getDate(),
            }
            this.taskService.createTask(task)
            this.formCreateTask.reset()
            this.taskModalService.closeModal()
        } else {
            this.alertService.setAlert({
                type: AlertType.error,
                text: 'Formulario Invalido',
            })
        }
    }
}
