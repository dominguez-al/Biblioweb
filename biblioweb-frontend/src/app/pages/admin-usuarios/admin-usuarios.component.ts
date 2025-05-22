import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

import { UsuarioService, Usuario } from '../../services/usuario.service';

@Component({
  selector: 'app-admin-usuarios',
  standalone: false,
  templateUrl: './admin-usuarios.component.html',
  styleUrls: ['./admin-usuarios.component.scss']
})
export class AdminUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  nuevoUsuario: Usuario = {
    nombre: '',
    email: '',
    rol: '',
    password: ''
  };

  formVisible: boolean = false;
  modoEdicion: boolean = false;
  formularioIntentado: boolean = false;
  usuarioEditandoId: number | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  // Navegar hacia atrás
  volverAtras(): void {
    this.router.navigate(['/usuario-logueado']);
  }

  // Obtener todos los usuarios
  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe(data => {
      this.usuarios = data;
    });
  }

  // Validación de email
  validarEmail(email: string | undefined): boolean {
    if (!email) return false;
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
  }

  // Validación de contraseña segura
  validarPassword(password: string | undefined): boolean {
    if (!password) return false;
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\d\s]).{8,}$/;
    return regex.test(password);
  }

  // Crear nuevo usuario
  crearUsuario(): void {
    this.formularioIntentado = true;

    if (!this.validarEmail(this.nuevoUsuario.email)) return;
    if (!this.validarPassword(this.nuevoUsuario.password)) return;

    this.usuarioService.crearUsuario(this.nuevoUsuario).subscribe(() => {
      this.resetFormulario();
      this.cargarUsuarios();

      Swal.fire({
        icon: 'success',
        title: 'Usuario creado',
        text: 'El nuevo usuario ha sido registrado correctamente.',
        confirmButtonColor: '#3085d6'
      });
    });
  }

  // Mostrar formulario para crear
  mostrarFormularioCrear(): void {
    this.resetFormulario();
    this.formVisible = true;
  }

  // Editar usuario
  editarUsuario(usuario: Usuario): void {
    this.nuevoUsuario = { ...usuario };
    this.modoEdicion = true;
    this.usuarioEditandoId = usuario.id || null;
    this.formVisible = true;
  }

  // Actualizar usuario existente
  actualizarUsuario(): void {
    if (!this.usuarioEditandoId) return;

    Swal.fire({
      title: 'Actualizar usuario',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No'
    }).then(result => {
      if (result.isConfirmed) {
        this.usuarioService.actualizarUsuario(this.usuarioEditandoId!, this.nuevoUsuario).subscribe(() => {
          this.resetFormulario();
          this.cargarUsuarios();

          Swal.fire('Actualizado', 'El usuario ha sido actualizado.', 'success');
        });
      }
    });
  }

  // Cancelar creación o edición
  cancelarEdicion(): void {
    this.resetFormulario();
  }

  // Eliminar usuario
  eliminarUsuario(id: number): void {
    Swal.fire({
      title: '¿Eliminar usuario?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: 'Eliminando...',
          allowOutsideClick: false,
          didOpen: () => Swal.showLoading()
        });

        this.usuarioService.eliminarUsuario(id).subscribe({
          next: () => {
            this.cargarUsuarios();
            Swal.fire('Eliminado', 'El usuario ha sido eliminado correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar el usuario. Intenta nuevamente.', 'error');
          }
        });
      }
    });
  }

  // Reutilizado para limpiar estado de formulario
  private resetFormulario(): void {
    this.modoEdicion = false;
    this.formularioIntentado = false;
    this.usuarioEditandoId = null;
    this.formVisible = false;
    this.nuevoUsuario = { nombre: '', email: '', rol: '', password: '' };
  }
}
