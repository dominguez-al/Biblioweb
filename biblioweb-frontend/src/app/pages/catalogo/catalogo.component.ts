import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LibroService, Libro } from '../../services/libro.service';
import { MatDialog } from '@angular/material/dialog';
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
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
      this.librosFiltrados = data;
      this.generosDisponibles = [...new Set(data.map(libro => libro.genero))]; // extrae géneros únicos
    });
  }

  filtrarPorGenero(): void {
    if (this.generoSeleccionado === '') {
      this.librosFiltrados = this.libros;
    } else {
      this.librosFiltrados = this.libros.filter(
        libro => libro.genero === this.generoSeleccionado
      );
    }
  }

  verDetalles(libro: Libro): void {
    const dialogRef = this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal',
    });

    dialogRef.afterClosed().subscribe((resultado) => {
      if (resultado === true) {
        this.actualizarLibros(); // ahora lo creamos
      }
    });
  }

  actualizarLibros(): void {
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
    });
  }

}



