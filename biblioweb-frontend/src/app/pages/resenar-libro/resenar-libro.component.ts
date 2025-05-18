import { Component, OnInit } from '@angular/core';
import { LibroService, Libro } from '../../services/libro.service';
import { TokenService } from '../../services/token.service';
import { ResenarService } from '../../services/resenar.service';

@Component({
  selector: 'app-resena-libros',
  standalone:false,
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
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.libroService.obtenerLibros().subscribe((data) => {
      this.libros = data;
    });
  }

enviarResena() {
  const email = this.tokenService.getEmail();
  if (!email || !this.libroId || !this.comentario) {
    alert('Por favor completa todos los campos');
    return;
  }

  this.resenarService.crearResena(this.libroId, email, this.comentario, this.puntuacion).subscribe({
    next: () => {
      alert('✅ Reseña enviada correctamente');
      this.comentario = '';
      this.puntuacion = 5;
      this.libroId = 0;
    },
    error: () => {
      alert('❌ Error al enviar la reseña');
    }
  });
}
}
