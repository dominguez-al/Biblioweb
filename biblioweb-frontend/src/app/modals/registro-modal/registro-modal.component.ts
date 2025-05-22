import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService, UsuarioRegistro } from '../../services/auth.service';

@Component({
  selector: 'app-registro-modal',
  standalone: false,
  templateUrl: './registro-modal.component.html',
  styleUrls: ['./registro-modal.component.scss']
})
export class RegistroModalComponent {
  // Campos del formulario
  nombre: string = '';
  email: string = '';
  password: string = '';
  confirmarPassword: string = '';

  // Estado UI
  mensaje: string = '';
  error: string = '';
  verPassword: boolean = false;
  emailValido: boolean = false;
  passwordValida: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<RegistroModalComponent>,
    private authService: AuthService
  ) {}

  // Validación y envío del formulario
  onSubmit(): void {
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

  // Valida formato de email
  validarEmail(): void {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    this.emailValido = regex.test(this.email);
  }

  // Valida complejidad de contraseña
  validarPassword(): void {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\d\s]).{8,}$/;
    this.passwordValida = regex.test(this.password);
  }

  // Habilita el botón de registro si todo está correcto
  formularioValido(): boolean {
    return (
      this.nombre.trim().length > 0 &&
      this.emailValido &&
      this.passwordValida &&
      this.password === this.confirmarPassword
    );
  }

  // Cierra el modal
  cerrar(): void {
    this.dialogRef.close();
  }
}
