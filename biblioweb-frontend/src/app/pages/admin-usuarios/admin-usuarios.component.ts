import { Component, OnInit } from '@angular/core';
import { UsuarioService, Usuario } from '../../services/usuario.service';
import Swal from 'sweetalert2';


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
    rol: 'USER',
    password: ''
  };
  modoEdicion: boolean = false;
  usuarioEditandoId: number | null = null;

  // 🔹 NUEVA propiedad para controlar visibilidad del formulario
  formVisible: boolean = false;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe(data => {
      this.usuarios = data;
    });
  }

  crearUsuario(): void {
    this.usuarioService.crearUsuario(this.nuevoUsuario).subscribe(() => {
      this.nuevoUsuario = { nombre: '', email: '', rol: 'USER' };
      this.formVisible = false;
      this.cargarUsuarios();
    });
  }

  // 🔹 NUEVO método para mostrar el formulario
  mostrarFormularioCrear(): void {
    this.formVisible = true;
    this.modoEdicion = false;
    this.nuevoUsuario = { nombre: '', email: '', rol: 'USER' };
  }

  editarUsuario(usuario: Usuario): void {
    this.nuevoUsuario = { ...usuario };
    this.modoEdicion = true;
    this.usuarioEditandoId = usuario.id || null;
    this.formVisible = true; // mostrar el formulario también
  }

  actualizarUsuario(): void {
    if (!this.usuarioEditandoId) return;

    this.usuarioService.actualizarUsuario(this.usuarioEditandoId, this.nuevoUsuario).subscribe(() => {
      this.modoEdicion = false;
      this.usuarioEditandoId = null;
      this.nuevoUsuario = { nombre: '', email: '', rol: 'USER' };
      this.formVisible = false;
      this.cargarUsuarios();
    });
  }

  cancelarEdicion(): void {
    this.modoEdicion = false;
    this.usuarioEditandoId = null;
    this.formVisible = false;
    this.nuevoUsuario = { nombre: '', email: '', rol: 'USER' };
  }

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

}
