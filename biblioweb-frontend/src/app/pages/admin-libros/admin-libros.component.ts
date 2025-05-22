import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

import { LibroService, Libro, ReservaLibro } from '../../services/libro.service';
import { ReservaLibroService } from '../../services/reserva-libro.service';

@Component({
  selector: 'app-admin-libros',
  standalone: false,
  templateUrl: './admin-libros.component.html',
  styleUrls: ['./admin-libros.component.scss']
})
export class AdminLibrosComponent implements OnInit {
  libros: Libro[] = [];
  reservas: ReservaLibro[] = [];

  nuevoLibro: Libro = {
    titulo: '',
    autor: '',
    genero: '',
    estado: 'DISPONIBLE',
    fechaAltaLibro: new Date().toISOString(),
    imagen: ''
  };

  constructor(
    private libroService: LibroService,
    private reservaLibroService: ReservaLibroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
    this.cargarReservas();
  }

  // Crear un nuevo libro
  crearLibro(): void {
    this.libroService.crearLibro(this.nuevoLibro).subscribe({
      next: () => {
        Swal.fire('¡Libro creado!', 'El libro se ha agregado correctamente.', 'success');
        this.nuevoLibro = {
          titulo: '',
          autor: '',
          genero: '',
          estado: 'DISPONIBLE',
          fechaAltaLibro: new Date().toISOString(),
          imagen: ''
        };
        this.cargarLibros();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo crear el libro. Intenta nuevamente.', 'error');
      }
    });
  }

  // Eliminar un libro por ID
  eliminarLibro(id: number): void {
    Swal.fire({
      title: '¿Eliminar libro?',
      text: 'Esta acción eliminará el libro del sistema.',
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

        this.libroService.eliminarLibro(id).subscribe({
          next: () => {
            this.cargarLibros();
            Swal.fire('Eliminado', 'El libro fue eliminado correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar el libro.', 'error');
          }
        });
      }
    });
  }

  // Cancelar una reserva de libro
  cancelarReserva(id: number): void {
    if (!id) {
      Swal.fire('ID inválido', 'ID de reserva no válido.', 'warning');
      return;
    }

    Swal.fire({
      title: '¿Cancelar reserva?',
      text: 'Esto liberará el libro reservado.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'Volver',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: 'Cancelando...',
          allowOutsideClick: false,
          didOpen: () => Swal.showLoading()
        });

        this.reservaLibroService.cancelarReserva(id).subscribe({
          next: () => {
            this.cargarLibros();
            this.cargarReservas();
            Swal.fire('Cancelada', 'La reserva fue cancelada correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo cancelar la reserva.', 'error');
          }
        });
      }
    });
  }

  // Cargar todos los libros
  cargarLibros(): void {
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
    });
  }

  // Cargar todas las reservas activas
  cargarReservas(): void {
    this.libroService.obtenerReservas().subscribe(data => {
      this.reservas = data;
    });
  }

  // Navegar a la vista anterior
  volverAtras(): void {
    this.router.navigate(['/usuario-logueado']);
  }
}
