import { Component, OnInit } from '@angular/core';
import { AulaService } from '../../services/aula.service';
import { TokenService } from '../../services/token.service';
import { ReservaAulaService } from '../../services/reserva-aula.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-aulas',
  standalone: false,
  templateUrl: './listar-aulas.component.html',
  styleUrls: ['./listar-aulas.component.scss']
})
export class ListarAulasComponent implements OnInit {
  aulas: any[] = [];
  idUsuario!: number;
  fechasSeleccionadas: { [idAula: number]: string } = {};
  fechasOcupadas: { [idAula: number]: string[] } = {};
  hoy: string = new Date().toISOString().split('T')[0];
  maxFecha: string = new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0];

  constructor(
    private aulaService: AulaService,
    private tokenService: TokenService,
    private reservaAulaService: ReservaAulaService
  ) {}

  ngOnInit(): void {
    const payload = this.tokenService.getPayload();
    if (payload?.idUsuario) {
      this.idUsuario = payload.idUsuario;
      console.log("🧪 ID de usuario:", this.idUsuario);
    } else {
      console.error("❌ No se pudo obtener el ID del usuario del token");
    }

    this.aulaService.obtenerAulas().subscribe(aulas => {
      this.aulas = aulas;
      console.log('📋 Aulas cargadas:', this.aulas);

      this.aulas.forEach(aula => {
        this.cargarFechasOcupadas(aula.id);
      });
    });
  }

  getClaseEstado(estado: string): string {
    return (estado || '').trim().toLowerCase();
  }

  estaOcupada(idAula: number, fecha: string): boolean {
    return this.fechasOcupadas[idAula]?.includes(fecha);
  }

  cargarFechasOcupadas(idAula: number) {
    this.reservaAulaService.obtenerFechasOcupadas(idAula).subscribe(fechas => {
      this.fechasOcupadas[idAula] = fechas.map(f => f.toString());
    });
  }

  onFechaSeleccionada(fecha: string, idAula: number): void {
    this.fechasSeleccionadas[idAula] = fecha;
  }

  reservarAula(idAula: number) {
    const fecha = this.fechasSeleccionadas[idAula];
    if (!fecha) {
      Swal.fire('Fecha no seleccionada', 'Por favor, selecciona una fecha antes de reservar.', 'warning');
      return;
    }

    if (this.estaOcupada(idAula, fecha)) {
      Swal.fire('Fecha ocupada', '⚠️ Esta fecha ya está ocupada para el aula seleccionada.', 'warning');
      return;
    }

    const payload = {
      idUsuario: this.idUsuario,
      idAula: idAula,
      fechaReservaAula: fecha
    };

    console.log("📦 Payload enviado:", payload);

    Swal.fire({
      title: 'Reservando aula...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.reservaAulaService.crearReserva(payload).subscribe({
      next: (res: any) => {
        console.log("✅ Reserva creada", res);
        const fechaFormateada = new Intl.DateTimeFormat('es-ES').format(new Date(fecha));
        Swal.fire('¡Reservada!', `✅ Aula reservada para el día ${fechaFormateada}`, 'success');

        this.cargarFechasOcupadas(idAula);

        setTimeout(() => {
          this.fechasSeleccionadas[idAula] = '';
        }, 100);
      },
      error: (err: any) => {
        console.error("❌ Error al reservar aula:", err);
        Swal.fire('Error', '❌ No se pudo completar la reserva. Intenta con otra fecha.', 'error');
      }
    });
  }
}
