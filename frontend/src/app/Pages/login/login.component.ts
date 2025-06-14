import { Component, inject, signal } from '@angular/core'
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule,
    ValidatorFn,
    AbstractControl,
    ValidationErrors,
} from '@angular/forms'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { CommonModule } from '@angular/common'
import { LoginModel } from '../../Core/models/login.model'
import { AlertService } from '../../Core/Services/alertService/alert.service'
import { AlertType } from '../../Core/Enums/alertType.enum'
import { AuthService } from '../../Core/Services/authService/auth.service'

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        CommonModule,
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css',
})
export class LoginComponent {
    formLogin: FormGroup = new FormGroup({
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
    })

    formRegister: FormGroup = new FormGroup({
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
        passwordCheck: new FormControl('', [
            Validators.required,
            this.confirmPassword(),
        ]),
    })

    isLoginTouched = signal(true)
    isRegisterTouched = signal(false)

    authService = inject(AuthService)
    alertService = inject(AlertService)

    onLoginSubmit() {
        if (this.formLogin.valid) {
            var loginModel: LoginModel = {
                username: this.formLogin.get('username')?.value,
                password: this.formLogin.get('password')?.value,
            }
            this.authService.login(loginModel)
        } else {
            this.alertService.setAlert({
                type: AlertType.error,
                text: 'Formulario Invalido',
            })
        }
    }

    onRegisterSubmit() {
        if (this.formRegister.valid) {
            var loginModel: LoginModel = {
                username: this.formRegister.get('username')?.value,
                password: this.formRegister.get('password')?.value,
            }
            this.authService.register(loginModel)
            this.formRegister.reset()
        } else {
            this.alertService.setAlert({
                type: AlertType.error,
                text: 'Formulario Invalido',
            })
        }
    }

    confirmPassword(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            return control.value != null &&
                control.value == control.parent?.get('password')?.value
                ? null
                : { confirmPassword: 'error' }
        }
    }
}
