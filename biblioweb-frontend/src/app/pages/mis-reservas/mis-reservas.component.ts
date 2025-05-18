import { Component, OnInit } from '@angular/core';
import { ReservaLibroService } from '../../services/reserva-libro.service';
import { ReservaAulaService } from '../../services/reserva-aula.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-mis-reservas',
  standalone: false,
  templateUrl: './mis-reservas.component.html',
  styleUrls: ['./mis-reservas.component.scss']
})
export class MisReservasComponent implements OnInit {
  reservasLibros: any[] = [];
  reservasAulas: any[] = [];

  constructor(
    private reservaLibroService: ReservaLibroService,
    private reservaAulaService: ReservaAulaService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    const idUsuario = this.tokenService.getUserId();
    if (!idUsuario) return;

    this.reservaLibroService.obtenerReservasUsuario(idUsuario).subscribe(libros => {
      this.reservasLibros = libros;
    });

    this.reservaAulaService.obtenerReservasUsuario(idUsuario).subscribe(aulas => {
      this.reservasAulas = aulas;
    });

  }

  cancelarLibro(id: number): void {
    if (confirm('¿Cancelar esta reserva de libro?')) {
      this.reservaLibroService.cancelarReserva(id).subscribe(() => {
        this.reservasLibros = this.reservasLibros.filter(r => r.id !== id);
      });
    }
  }

  cancelarAula(id: number): void {
    if (confirm('¿Cancelar esta reserva de aula?')) {
      this.reservaAulaService.cancelarReserva(id).subscribe(() => {
        const idUsuario = this.tokenService.getUserId();
        if (idUsuario === null) return;

        this.reservaAulaService.obtenerReservasUsuario(idUsuario).subscribe(aulas => {
          this.reservasAulas = aulas;
        });
      });
    }
}


}
