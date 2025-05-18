import { Component, OnInit } from '@angular/core';
import { LibroService, Libro } from '../../services/libro.service';
import { ReservaLibroService } from '../../services/reserva-libro.service';
import { ReservaLibro } from '../../services/libro.service';




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
    if (confirm('¿Seguro que deseas eliminar este libro?')) {
      this.libroService.eliminarLibro(id).subscribe(() => {
        this.cargarLibros();
      });
    }
  }

cancelarReserva(id: number): void {
console.log('ID de reserva recibido para cancelar:', id);
  if (!id) {
    alert('ID de reserva no válido');
    return;
  }

  if (confirm('¿Seguro que deseas cancelar esta reserva?')) {
    this.reservaLibroService.cancelarReserva(id).subscribe(() => {
      this.cargarLibros();
      this.cargarReservas();
    });
  }
}

    }
