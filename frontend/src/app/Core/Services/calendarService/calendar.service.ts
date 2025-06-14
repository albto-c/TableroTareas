import { Injectable, signal } from '@angular/core'

@Injectable({
    providedIn: 'root',
})
export class CalendarService {
    constructor() {}

    setDate(date: string) {
        localStorage.setItem('date', date)
    }

    getDate() {
        let dateStorage = localStorage.getItem('date')
        return dateStorage != null ? dateStorage : ''
    }

    setLastMonthAndYearSelected(month: number, year: number) {
        localStorage.setItem('lastMonthAndYear', `${month.toString()}/${year}`)
    }

    getLastMonthSelected() {
        let monthAndYearStorage = localStorage.getItem('lastMonthAndYear')
        return monthAndYearStorage != null
            ? parseInt(monthAndYearStorage.split('/')[0])
            : null
    }

    getLastYearSelected() {
        let monthAndYearStorage = localStorage.getItem('lastMonthAndYear')
        return monthAndYearStorage != null
            ? parseInt(monthAndYearStorage.split('/')[1])
            : null
    }
}
