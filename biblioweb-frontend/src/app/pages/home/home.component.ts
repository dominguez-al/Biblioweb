import { Component, OnInit } from '@angular/core';
import { LibroService, Libro } from '../../services/libro.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LibroDetalleModalComponent } from '../../modals/libro-detalle-modal/libro-detalle-modal.component';
import { UsuarioLibroService } from '../../services/usuario-libro.service';
import { ResenarService } from '../../services/resenar.service';
import { NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

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
  resenas: any[] = []; // ⬅ añadimos array de reseñas

  constructor(
    private libroService: LibroService,
    private router: Router,
    private dialog: MatDialog,
    private usuarioLibroService: UsuarioLibroService,
    private resenarService: ResenarService // ⬅ inyectamos el servicio
  ) {}

  ngOnInit(): void {
    this.libroService.obtenerLibros().subscribe((data) => {
      this.libros = data;
    });

    this.libroService.obtenerUltimosLibros().subscribe((data) => {
      this.ultimosLibros = data;
    });

    this.libroService.obtenerLibrosPopulares().subscribe((data) => {
      this.librosPopulares = data;

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        if (event.urlAfterRedirects === '/home') {
          this.cargarResenas(); // recarga cuando vuelves
        }
    });
});


    this.resenarService.obtenerTodasResenas().subscribe({
      next: (data) => this.resenas = data,
      error: () => console.error('Error al cargar reseñas')
    });
  }

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
    if (termino === '') {
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
    this.terminoBusqueda = `${libro.titulo} ${libro.autor}`;
    this.resultados = [libro];
    this.sugerencias = [];
    this.terminoBusqueda = '';
  }

  verDetalles(libro: Libro): void {
    console.log('📘 Libro seleccionado:', libro);
    this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal',
    });
  }

  verDetallesPopular(libro: Libro): void {
    console.log('📘 Libro popular seleccionado:', libro);
    this.dialog.open(LibroDetalleModalComponent, {
      width: '500px',
      data: libro,
      panelClass: 'detalle-libro-modal',
    });
  }


  cargarResenas(): void {
  this.resenarService.obtenerTodasResenas().subscribe({
    next: (data) => this.resenas = data,
    error: () => console.error('Error al cargar reseñas')
  });
}

}
