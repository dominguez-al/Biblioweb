import { Component, OnInit } from '@angular/core';
import { ReservaAulaService } from '../../services/reserva-aula.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


@Component({
  selector: 'app-admin-aulas',
  standalone: false,
  templateUrl: './admin-aulas.component.html',
  styleUrl: './admin-aulas.component.scss'
})
export class AdminAulasComponent implements OnInit {
  reservasAulas: any[] = [];

  constructor(private reservaAulaService: ReservaAulaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaAulaService.obtenerTodasLasReservas().subscribe(data => {
      this.reservasAulas = data;
    });
  }

volverAtras(): void {
  this.router.navigate(['/usuario-logueado']);
}

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

