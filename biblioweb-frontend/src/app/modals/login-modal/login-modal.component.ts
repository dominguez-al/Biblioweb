import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login-modal',
  standalone: false,
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
  email = '';
  password = '';
  error = ''; // ✅ Añade esta línea

  constructor(
    public dialogRef: MatDialogRef<LoginModalComponent>,
    private authService: AuthService,
    private router: Router
  ) {}


  onSubmit() {
    if (!this.email || !this.password) {
      this.error = 'Debes completar todos los campos.';
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        this.error = '';
        this.dialogRef.close();
        this.router.navigate(['/usuario-logueado']); // ✅ redirige a tu sesión
      },
      error: (err) => {
        this.error = err.error || 'Credenciales inválidas';
      }
    });

  }

  cerrar() {
    this.dialogRef.close();
  }
}
