import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { filter } from 'rxjs/operators';

import { LibroService, Libro } from '../../services/libro.service';
import { UsuarioLibroService } from '../../services/usuario-libro.service';
import { ResenarService } from '../../services/resenar.service';
import { LibroDetalleModalComponent } from '../../modals/libro-detalle-modal/libro-detalle-modal.component';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  terminoBusqueda: string = '';
  libros: Libro[] = [];
  resultados: Libro[] = [];
  sugerencias: Libro[] = [];

  ultimosLibros: Libro[] = [];
  librosPopulares: Libro[] = [];
  resenas: any[] = [];

  constructor(
    private libroService: LibroService,
    private router: Router,
    private dialog: MatDialog,
    private usuarioLibroService: UsuarioLibroService,
    private resenarService: ResenarService
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
    this.cargarUltimosLibros();
    this.cargarLibrosPopulares();
    this.cargarResenas();

    // Escuchar navegación para recargar reseñas al volver al home
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        if (event.urlAfterRedirects === '/home') {
          this.cargarResenas();
        }
      });
  }

  // ---  CARGA DE DATOS ---

  private cargarLibros(): void {
    this.libroService.obtenerLibros().subscribe(data => {
      this.libros = data;
    });
  }

  private cargarUltimosLibros(): void {
    this.libroService.obtenerUltimosLibros().subscribe(data => {
      this.ultimosLibros = data;
    });
  }

  private cargarLibrosPopulares(): void {
    this.libroService.obtenerLibrosPopulares().subscribe(data => {
      this.librosPopulares = data;
    });
  }

 cargarResenas(): void {
  this.resenarService.obtenerTodasResenas().subscribe({
    next: (data) => {
      this.resenas = data;
      console.log('Reseñas cargadas:', this.resenas); // <- AÑADE ESTO para depurar
    },
    error: () => console.error('Error al cargar reseñas')
  });
}


  // ---  BÚSQUEDA Y SUGERENCIAS ---

  private normalizar(texto: string): string {
    return texto.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
  }

  filtrarSugerencias(): void {
    const termino = this.normalizar(this.terminoBusqueda);
    if (!termino) {
      this.sugerencias = [];
      return;
    }

    this.sugerencias = this.libros
      .filter(libro =>
        this.normalizar(`${libro.titulo} ${libro.autor}`).includes(termino)
      )
      .slice(0, 5);
  }

  buscar(): void {
    const termino = this.normalizar(this.terminoBusqueda.trim());
    if (!termino) {
      this.resultados = [];
      return;
    }

    this.resultados = this.libros.filter(libro =>
      this.normalizar(`${libro.titulo} ${libro.autor}`).includes(termino)
    );

    this.sugerencias = [];
    this.terminoBusqueda = '';
  }

  buscarDesdeSugerencia(libro: Libro): void {
    this.resultados = [libro];
    this.sugerencias = [];
    this.terminoBusqueda = '';
  }

  // ---  MODAL DETALLES ---

  verDetalles(libro: Libro): void {
    this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal',
    });
  }

  verDetallesPopular(libro: Libro): void {
    this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal',
    });
  }
}
