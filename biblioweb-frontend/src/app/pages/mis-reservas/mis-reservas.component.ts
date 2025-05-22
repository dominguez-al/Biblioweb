import { Component, OnInit } from '@angular/core';
import { ReservaLibroService } from '../../services/reserva-libro.service';
import { ReservaAulaService } from '../../services/reserva-aula.service';
import { TokenService } from '../../services/token.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

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
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idUsuario = this.tokenService.getUsuarioId();
    if (!idUsuario) return;

    this.reservaLibroService.obtenerReservasUsuario(idUsuario).subscribe(libros => {
      this.reservasLibros = libros;
    });

    this.reservaAulaService.obtenerReservasUsuario(idUsuario).subscribe(aulas => {
      this.reservasAulas = aulas;
    });
  }

volverAtras(): void {
  this.router.navigate(['/usuario-logueado']);
}

  cancelarLibro(id: number): void {
    Swal.fire({
      title: '¿Cancelar reserva de libro?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'No, volver',
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
            this.reservasLibros = this.reservasLibros.filter(r => r.id !== id);
            Swal.fire('Cancelado', 'La reserva fue cancelada correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'Ocurrió un problema al cancelar la reserva.', 'error');
          }
        });
      }
    });
  }

  cancelarAula(id: number): void {
    Swal.fire({
      title: '¿Cancelar reserva de aula?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'No, volver',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: 'Cancelando...',
          allowOutsideClick: false,
          didOpen: () => Swal.showLoading()
        });

        this.reservaAulaService.cancelarReserva(id).subscribe({
          next: () => {
            const idUsuario = this.tokenService.getUsuarioId();
            if (!idUsuario) return;

            this.reservaAulaService.obtenerReservasUsuario(idUsuario).subscribe(aulas => {
              this.reservasAulas = aulas;
              Swal.fire('Cancelado', 'La reserva fue cancelada correctamente.', 'success');
            });
          },
          error: () => {
            Swal.fire('Error', 'Ocurrió un problema al cancelar la reserva.', 'error');
          }
        });
      }
    });
  }
}
