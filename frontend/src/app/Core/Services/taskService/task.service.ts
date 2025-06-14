import { HttpClient } from '@angular/common/http'
import { inject, Injectable } from '@angular/core'
import { catchError, EMPTY, map, Subject, switchMap, tap } from 'rxjs'
import { TaskDto } from '../../models/taskDto.dto'
import { CalendarService } from '../calendarService/calendar.service'
import { TaskCDto } from '../../models/taskCDto.dto'

@Injectable({
    providedIn: 'root',
})
export class TaskService {
    constructor() {}

    httpClient = inject(HttpClient)
    calendarService = inject(CalendarService)

    private tasksList$ = new Subject<TaskDto[]>()

    getTaskByMonthAndYear(month: string, year: string) {
        let url = `http://localhost:8080/task/byMonthAndYear/${month}/${year}`
        return this.httpClient.get<any>(url)
    }

    getTaskByDate(date: string) {
        let url = `http://localhost:8080/task/byDate/${date}`
        return this.httpClient.get<any>(url).pipe(
            tap(res => {
                if (res != null) {
                    this.tasksList$.next(res.tasksList)
                }
            }),
            catchError(() => {
                this.tasksList$.next([])
                return EMPTY
            })
        )
    }

    createTask(task: TaskCDto) {
        let url = 'http://localhost:8080/task/create'
        let body = JSON.stringify(task)
        return this.httpClient
            .post<any>(url, body, {
                headers: { 'Content-Type': 'application/json' },
            })
            .pipe(
                switchMap(() =>
                    this.getTaskByDate(this.calendarService.getDate())
                ),
                catchError(() => {
                    this.tasksList$.next([])
                    return EMPTY
                })
            )
            .subscribe(res => {
                if (res != null) {
                    this.tasksList$.next(res.tasksList)
                }
            })
    }

    deleteTask(id: number) {
        let url = `http://localhost:8080/task/delete/${id}`
        this.httpClient
            .delete<any>(url)
            .pipe(
                switchMap(() =>
                    this.getTaskByDate(this.calendarService.getDate())
                ),
                catchError(() => {
                    this.tasksList$.next([])
                    return EMPTY
                })
            )
            .subscribe(res => {
                if (res != null) {
                    this.tasksList$.next(res.tasksList)
                }
            })
    }

    updateTaskStatus(idTask: number, idTaskStatus: number) {
        let url = `http://localhost:8080/task/update/${idTask}/${idTaskStatus}`
        this.httpClient
            .put<any>(url, null)
            .pipe(
                switchMap(() =>
                    this.getTaskByDate(this.calendarService.getDate())
                ),
                catchError(() => {
                    this.tasksList$.next([])
                    return EMPTY
                })
            )
            .subscribe(res => {
                if (res != null) {
                    this.tasksList$.next(res.tasksList)
                }
            })
    }

    getTasksList() {
        return this.tasksList$.asObservable()
    }
}
