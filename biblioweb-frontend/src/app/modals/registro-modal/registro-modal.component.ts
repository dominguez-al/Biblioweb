import { Component } from '@angular/core';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { AuthService, UsuarioRegistro } from '../../services/auth.service';


@Component({
  selector: 'app-registro-modal',
  standalone: false,
  templateUrl: './registro-modal.component.html',
  styleUrls: ['./registro-modal.component.scss']
})
export class RegistroModalComponent {
  nombre = '';
  email = '';
  password = '';
  confirmarPassword = '';
  mensaje = '';
  error = '';

  verPassword = false;
  emailValido = false;
  passwordValida = false;

  constructor(
    public dialogRef: MatDialogRef<RegistroModalComponent>,
    private authService: AuthService
  ) {}

  onSubmit() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\d\s]).{8,}$/;

    if (!emailRegex.test(this.email)) {
      this.error = 'Correo electrónico inválido.';
      return;
    }

    if (!passwordRegex.test(this.password)) {
      this.error = 'La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo.';
      return;
    }

    if (this.password !== this.confirmarPassword) {
      this.error = 'Las contraseñas no coinciden.';
      return;
    }

    const nuevoUsuario: UsuarioRegistro = {
      nombre: this.nombre,
      email: this.email,
      password: this.password
    };

    this.authService.registrar(nuevoUsuario).subscribe({
      next: (msg) => {
        this.mensaje = msg;
        this.error = '';
        setTimeout(() => this.dialogRef.close(), 1500);
      },
      error: (err) => {
        this.error = typeof err.error === 'string' ? err.error : 'Correo ya registrado';
        this.mensaje = '';
      }
    });
  }

  validarEmail() {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    this.emailValido = regex.test(this.email);
  }

  validarPassword() {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\d\s]).{8,}$/;
    this.passwordValida = regex.test(this.password);
  }

  formularioValido(): boolean {
    return (
      this.nombre.trim().length > 0 &&
      this.emailValido &&
      this.passwordValida &&
      this.password === this.confirmarPassword
    );
  }

  cerrar() {
    this.dialogRef.close();
  }
}
