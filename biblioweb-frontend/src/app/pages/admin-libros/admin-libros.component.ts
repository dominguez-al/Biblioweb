import { Component, OnInit } from '@angular/core';
import { LibroService, Libro } from '../../services/libro.service';
import { ReservaLibroService } from '../../services/reserva-libro.service';
import { ReservaLibro } from '../../services/libro.service';
import Swal from 'sweetalert2';




@Component({
  selector: 'app-admin-libros',
  standalone: false,
  templateUrl: './admin-libros.component.html',
  styleUrl: './admin-libros.component.scss'
})

export class AdminLibrosComponent implements OnInit {
  reservas: ReservaLibro[] = [];
  libros: Libro[] = [];
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
    private reservaLibroService: ReservaLibroService
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
    this.cargarReservas();
  }

  crearLibro(): void {
  this.libroService.crearLibro(this.nuevoLibro).subscribe(() => {
    this.nuevoLibro = {
      titulo: '',
      autor: '',
      genero: '',
      estado: 'DISPONIBLE',
      fechaAltaLibro: new Date().toISOString(),
      imagen: ''
    };
    this.cargarLibros();
  });
}

cargarLibros(): void {
  this.libroService.obtenerLibros().subscribe(data => {
        console.log("Libros actualizados:", data); // 💡 esto debe aparecer al crear
    this.libros = data;
  });
}

cargarReservas(): void {
  this.libroService.obtenerReservas().subscribe(data => {
    this.reservas = data;
  });
}

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


cancelarReserva(id: number): void {
  console.log('ID de reserva recibido para cancelar:', id);
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


    }
