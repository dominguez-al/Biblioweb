import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-modal',
  standalone: false,
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
  email: string = '';
  password: string = '';
  error: string = '';

  constructor(
    public dialogRef: MatDialogRef<LoginModalComponent>,
    private authService: AuthService,
    private router: Router
  ) {}

  // Envía los datos del formulario
  onSubmit(): void {
    if (!this.email || !this.password) {
      this.error = 'Debes completar todos los campos.';
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        this.error = '';
        this.dialogRef.close();
        this.router.navigate(['/usuario-logueado']);
      },
      error: (err) => {
        this.error = err.error || 'Credenciales inválidas';
      }
    });
  }

  // Cierra el modal sin hacer login
  cerrar(): void {
    this.dialogRef.close();
  }
}
