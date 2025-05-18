import { Component, OnInit } from '@angular/core';
import { ReservaAulaService } from '../../services/reserva-aula.service';

@Component({
  selector: 'app-admin-aulas',
  standalone: false,
  templateUrl: './admin-aulas.component.html',
  styleUrl: './admin-aulas.component.scss'
})
export class AdminAulasComponent implements OnInit {
  reservasAulas: any[] = [];

  constructor(private reservaAulaService: ReservaAulaService) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaAulaService.obtenerTodasLasReservas().subscribe(data => {
      this.reservasAulas = data;
    });
  }

  cancelarReserva(id: number): void {
    if (!id) {
      alert('ID de reserva no válido');
      return;
    }

    if (confirm('¿Estás seguro de que deseas cancelar esta reserva?')) {
      this.reservaAulaService.cancelarReserva(id).subscribe(() => {
        this.cargarReservas(); // ✅ Este método sí existe
      });
    }
  }
}

