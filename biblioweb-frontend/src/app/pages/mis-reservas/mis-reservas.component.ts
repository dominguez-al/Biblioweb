import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

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
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idUsuario = this.tokenService.getUsuarioId();
    if (!idUsuario) return;

    this.cargarReservasLibros(idUsuario);
    this.cargarReservasAulas(idUsuario);
  }

  //  Navega al panel de usuario
  volverAtras(): void {
    this.router.navigate(['/usuario-logueado']);
  }

  //  Cargar reservas de libros
  private cargarReservasLibros(idUsuario: number): void {
    this.reservaLibroService.obtenerReservasUsuario(idUsuario).subscribe(libros => {
      this.reservasLibros = libros;
    });
  }

  //  Cargar reservas de aulas
  private cargarReservasAulas(idUsuario: number): void {
    this.reservaAulaService.obtenerReservasUsuario(idUsuario).subscribe(aulas => {
      this.reservasAulas = aulas;
    });
  }

  //  Cancelar reserva de libro
  cancelarLibro(id: number): void {
    this.confirmarCancelacion('libro').then(confirmado => {
      if (confirmado) {
        this.reservaLibroService.cancelarReserva(id).subscribe({
          next: () => {
            this.reservasLibros = this.reservasLibros.filter(r => r.id !== id);
            this.mostrarExito('La reserva fue cancelada correctamente.');
          },
          error: () => this.mostrarError()
        });
      }
    });
  }

  //  Cancelar reserva de aula
  cancelarAula(id: number): void {
    this.confirmarCancelacion('aula').then(confirmado => {
      if (confirmado) {
        this.reservaAulaService.cancelarReserva(id).subscribe({
          next: () => {
            const idUsuario = this.tokenService.getUsuarioId();
            if (idUsuario) {
              this.cargarReservasAulas(idUsuario);
              this.mostrarExito('La reserva fue cancelada correctamente.');
            }
          },
          error: () => this.mostrarError()
        });
      }
    });
  }

  //  Confirmación previa
  private confirmarCancelacion(tipo: 'libro' | 'aula'): Promise<boolean> {
    return Swal.fire({
      title: `¿Cancelar reserva de ${tipo}?`,
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'No, volver',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then(result => result.isConfirmed);
  }

  //  Mensaje de éxito
  private mostrarExito(mensaje: string): void {
    Swal.fire('Cancelado', mensaje, 'success');
  }

  //  Mensaje de error
  private mostrarError(): void {
    Swal.fire('Error', 'Ocurrió un problema al cancelar la reserva.', 'error');
  }
}
