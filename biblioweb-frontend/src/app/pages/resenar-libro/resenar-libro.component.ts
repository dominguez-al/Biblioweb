import { Component, OnInit } from '@angular/core';
import { LibroService, Libro } from '../../services/libro.service';
import { TokenService } from '../../services/token.service';
import { ResenarService } from '../../services/resenar.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-resena-libros',
  standalone: false,
  templateUrl: './resenar-libro.component.html',
  styleUrls: ['./resenar-libro.component.scss']
})
export class ResenarLibroComponent implements OnInit {
  libros: Libro[] = [];
  libroId: number = 0;
  comentario: string = '';
  puntuacion: number = 5;
  mensaje = '';

  constructor(
    private libroService: LibroService,
    private resenarService: ResenarService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  // Cargar lista de libros al iniciar el componente
  ngOnInit(): void {
    this.libroService.obtenerLibros().subscribe((data) => {
      this.libros = data;
    });
  }

  // Navegar al panel del usuario logueado
  volverAtras(): void {
    this.router.navigate(['/usuario-logueado']);
  }

  // Enviar reseña del usuario para un libro
  enviarResena(): void {
    const email = this.tokenService.getEmail();

    if (!email || !this.libroId || !this.comentario) {
      Swal.fire(
        'Campos incompletos',
        'Por favor completa todos los campos antes de enviar la reseña.',
        'warning'
      );
      return;
    }

    Swal.fire({
      title: 'Enviando reseña...',
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading()
    });

    this.resenarService.crearResena(this.libroId, email, this.comentario, this.puntuacion).subscribe({
      next: () => {
        Swal.fire('¡Gracias!', 'Reseña enviada correctamente.', 'success');
        this.comentario = '';
        this.puntuacion = 5;
        this.libroId = 0;
      },
      error: () => {
        Swal.fire('Error', 'No se pudo enviar la reseña. Intenta nuevamente.', 'error');
      }
    });
  }
}
