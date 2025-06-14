import { Routes } from '@angular/router'
import { TaskDashBoardComponent } from './Pages/task-dashboard/task-dashboard.component'
import { authGuard } from './Core/Guards/authGuard/auth.guard'
import { InitialLayoutComponent } from './Layouts/initial-layout/initial-layout.component'
import { CalendarComponent } from './Pages/calendar/calendar.component'
import { NotFoundComponent } from './Pages/not-found/not-found.component'
import { EmptyLayoutComponent } from './Layouts/empty-layout/empty-layout.component'
import { LoginComponent } from './Pages/login/login.component'

export const routes: Routes = [
    {
        path: '',
        component: InitialLayoutComponent,
        children: [
            {
                path: '',
                component: CalendarComponent,
            },
            {
                path: 'tablero/:date',
                component: TaskDashBoardComponent,
                canActivate: [authGuard],
            },
        ],
    },
    {
        path: '',
        component: EmptyLayoutComponent,
        children: [
            {
                path: 'login',
                component: LoginComponent,
            },
        ],
    },
    {
        path: '**',
        component: NotFoundComponent,
    },
]
