import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

import { ReservaAulaService } from '../../services/reserva-aula.service';

@Component({
  selector: 'app-admin-aulas',
  standalone: false,
  templateUrl: './admin-aulas.component.html',
  styleUrls: ['./admin-aulas.component.scss']
})
export class AdminAulasComponent implements OnInit {
  reservasAulas: any[] = [];

  constructor(
    private reservaAulaService: ReservaAulaService,
    private router: Router
  ) {}

  // Carga inicial de reservas
  ngOnInit(): void {
    this.cargarReservas();
  }

  // Obtiene todas las reservas desde el servicio
  cargarReservas(): void {
    this.reservaAulaService.obtenerTodasLasReservas().subscribe(data => {
      this.reservasAulas = data;
    });
  }

  // Navega hacia la vista del usuario logueado
  volverAtras(): void {
    this.router.navigate(['/usuario-logueado']);
  }

  // Cancela una reserva de aula
  cancelarReserva(id: number): void {
    if (!id) {
      Swal.fire('ID inválido', 'ID de reserva no válido.', 'warning');
      return;
    }

    Swal.fire({
      title: '¿Cancelar reserva?',
      text: 'Esta acción liberará el aula reservada.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'Volver',
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
            this.cargarReservas();
            Swal.fire('Cancelada', 'La reserva fue cancelada correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo cancelar la reserva.', 'error');
          }
        });
      }
    });
  }
}
