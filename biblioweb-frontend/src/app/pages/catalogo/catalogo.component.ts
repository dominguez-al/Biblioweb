import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { LibroService, Libro } from '../../services/libro.service';
import { LibroDetalleModalComponent } from '../../modals/libro-detalle-modal/libro-detalle-modal.component';

@Component({
  selector: 'app-catalogo',
  standalone: false,
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.scss']
})
export class CatalogoComponent implements OnInit {
  libros: Libro[] = [];
  librosFiltrados: Libro[] = [];
  generosDisponibles: string[] = [];
  generoSeleccionado: string = '';

  constructor(
    private libroService: LibroService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
  }

  // Carga inicial de libros y géneros únicos
  private cargarLibros(): void {
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
      this.librosFiltrados = data;
      this.generosDisponibles = [...new Set(data.map(libro => libro.genero))];
    });
  }

  // Filtro por género desde el select
  filtrarPorGenero(): void {
    this.librosFiltrados = this.generoSeleccionado
      ? this.libros.filter(libro => libro.genero === this.generoSeleccionado)
      : this.libros;
  }

  // Abre el modal con detalle del libro
  verDetalles(libro: Libro): void {
    const dialogRef = this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal'
    });

    dialogRef.afterClosed().subscribe((resultado) => {
      if (resultado === true) {
        this.actualizarLibros();
      }
    });
  }

  // Actualiza la lista de libros tras reserva/cambio
  private actualizarLibros(): void {
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
      this.filtrarPorGenero(); // mantener el filtro actual aplicado
    });
  }
}
