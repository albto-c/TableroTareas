import { Component, inject, signal } from '@angular/core'
import { DayModel } from '../../Core/models/day.model'
import { TaskService } from '../../Core/Services/taskService/task.service'
import { AuthService } from '../../Core/Services/authService/auth.service'
import { TaskModel } from '../../Core/models/task.model'
import { catchError, EMPTY, Observable, Subject, takeUntil } from 'rxjs'
import { RouterLink } from '@angular/router'
import { CalendarService } from '../../Core/Services/calendarService/calendar.service'
import { MatProgressSpinner } from '@angular/material/progress-spinner'
import { ToolTipComponent } from '../../Shared/Components/tool-tip/tool-tip.component'

@Component({
    selector: 'app-calendar',
    standalone: true,
    imports: [RouterLink, MatProgressSpinner, ToolTipComponent],
    templateUrl: './calendar.component.html',
    styleUrl: './calendar.component.css',
})
export class CalendarComponent {
    taskService = inject(TaskService)
    authService = inject(AuthService)
    calendarService = inject(CalendarService)

    TOTAL_DAYS_CALENDAR: number = 42

    UP: string = 'UP'
    DOWN: string = 'DOWN'

    nowDate = new Date()
    day = this.nowDate.getUTCDate()
    month = this.nowDate.getUTCMonth()
    year = this.nowDate.getUTCFullYear()

    months = [
        'Enero',
        'Febrero',
        'Marzo',
        'Abril',
        'Mayo',
        'Junio',
        'Julio',
        'Agosto',
        'Septiembre',
        'Octubre',
        'Noviembre',
        'Diciembre',
    ]
    daysOfWeek = ['L', 'M', 'X', 'J', 'V', 'S', 'D']

    daysOfMonth: DayModel[] = new Array(this.TOTAL_DAYS_CALENDAR)
    listTasksByMonthAndYear: TaskModel[] = []
    totalMonthDays = this.getTotalDaysOfMonth(this.month, this.year)

    loadCalendar = signal(false)
    private destroy$ = new Subject<void>()

    ngOnInit() {
        let lastMonthSelected = this.calendarService.getLastMonthSelected()
        let lastYearSelected = this.calendarService.getLastYearSelected()
        if (lastMonthSelected != null && lastYearSelected != null) {
            this.month = lastMonthSelected
            this.year = lastYearSelected
        }

        if (this.authService.isLogged$.getValue()) {
            this.getTasks().subscribe(res => {
                if (res != null && res.tasksList != null) {
                    this.listTasksByMonthAndYear = res.tasksList
                }
                this.generateDaysOfMonth()
                this.loadCalendar.set(true)
            })
        } else {
            this.generateDaysOfMonth()
            this.loadCalendar.set(true)
        }
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }

    changeMonth(direction: string) {
        this.loadCalendar.set(false)

        if (direction == this.UP) {
            if (this.month == 11) {
                this.month = 0
                this.year++
            } else {
                this.month += 1
            }
        } else if (direction == this.DOWN) {
            if (this.month == 0) {
                this.month = 11
                this.year--
            } else {
                this.month -= 1
            }
        }

        if (this.authService.isLogged$.getValue()) {
            this.listTasksByMonthAndYear.length = 0
            this.getTasks().subscribe(res => {
                if (res != null) {
                    this.listTasksByMonthAndYear = res.tasksList
                }
                this.generateDaysOfMonth()
                this.loadCalendar.set(true)
            })
        } else {
            this.generateDaysOfMonth()
            this.loadCalendar.set(true)
        }

        // Maintains the last month and year selected
        this.calendarService.setLastMonthAndYearSelected(this.month, this.year)
    }

    getTotalDaysOfMonth(month: number, year: number) {
        return new Date(year, month + 1, 0).getDate()
    }

    generateDaysOfMonth() {
        this.daysOfMonth.length = 0
        var firstDay = new Date(this.year, this.month, 1)
        var dayOfWeek = firstDay.getDay()

        this.totalMonthDays = this.getTotalDaysOfMonth(this.month, this.year)

        dayOfWeek === 0 ? (dayOfWeek = 6) : dayOfWeek--

        // The array is filled with the days of the previous month
        var totalDaysLastMonth = this.getTotalDaysOfMonth(
            this.month - 1,
            this.year
        )

        for (let i = 0; i < dayOfWeek; i++) {
            let dia: DayModel = {
                number: totalDaysLastMonth,
                currentMonth: false,
                taskStatus: 0,
            }

            this.daysOfMonth[i] = dia
            totalDaysLastMonth--
        }

        this.daysOfMonth = this.daysOfMonth.reverse()

        // The array is filled with the days of the current month
        for (
            let i = 1, j = this.daysOfMonth.length;
            i <= this.totalMonthDays;
            i++, j++
        ) {
            let day: DayModel = { number: i, currentMonth: true, taskStatus: 0 }

            // Task status is added to only the days of the current month
            if (this.authService.isLogged$.getValue()) {
                day.taskStatus = this.setTaskStatusOnDay(day.number)
            }

            this.daysOfMonth[j] = day
        }

        // The array is filled with the days of the following month
        for (
            let i = this.daysOfMonth.length, j = 1;
            i < this.TOTAL_DAYS_CALENDAR;
            i++, j++
        ) {
            let day: DayModel = {
                number: j,
                currentMonth: false,
                taskStatus: 0,
            }
            this.daysOfMonth[i] = day
        }
    }

    getTasks(): Observable<any> {
        let month = this.month + 1
        return this.taskService
            .getTaskByMonthAndYear(month.toString(), this.year.toString())
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.generateDaysOfMonth()
                    this.loadCalendar.set(true)
                    return EMPTY
                })
            )
    }

    setTaskStatusOnDay(day: number) {
        let tasksOfDay = this.listTasksByMonthAndYear.filter(
            task => new Date(task.date).getDate() == day
        )

        return tasksOfDay.length == 0
            ? 0
            : tasksOfDay.sort((a, b) => a.taskStatus - b.taskStatus)[0]
                  .taskStatus
    }

    getSelectedDate(day: number) {
        let month = this.month + 1
        this.calendarService.setDate(
            `${this.year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}`
        )
    }
}
