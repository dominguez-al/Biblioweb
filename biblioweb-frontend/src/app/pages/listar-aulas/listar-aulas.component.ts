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
  fechasSeleccionadas: { [idAula: number]: string } = {};
  fechasOcupadas: { [idAula: number]: string[] } = {};

  hoy: string = new Date().toISOString().split('T')[0];
  maxFecha: string = new Date(new Date().setMonth(new Date().getMonth() + 1))
    .toISOString()
    .split('T')[0];

  idUsuario!: number;

  constructor(
    private aulaService: AulaService,
    private tokenService: TokenService,
    private reservaAulaService: ReservaAulaService
  ) {}

  ngOnInit(): void {
    const payload = this.tokenService.getUsuarioDesdeToken();
    if (payload?.idUsuario) {
      this.idUsuario = payload.idUsuario;
    }

    this.aulaService.obtenerAulas().subscribe(aulas => {
      this.aulas = aulas;

      // Cargar fechas ocupadas para cada aula
      this.aulas.forEach(aula => {
        this.cargarFechasOcupadas(aula.id);
      });
    });
  }

  // Verifica si una fecha está ocupada para el aula
  estaOcupada(idAula: number, fecha: string): boolean {
    return this.fechasOcupadas[idAula]?.includes(fecha);
  }

  // Obtiene fechas ocupadas desde el backend
  private cargarFechasOcupadas(idAula: number): void {
    this.reservaAulaService.obtenerFechasOcupadas(idAula).subscribe(fechas => {
      this.fechasOcupadas[idAula] = fechas.map(f => f.toString());
    });
  }

  // Guarda la fecha seleccionada por aula
  onFechaSeleccionada(fecha: string, idAula: number): void {
    this.fechasSeleccionadas[idAula] = fecha;
  }

  // Desplaza la vista para que el input quede visible al enfocar
  scrollIntoViewOnFocus(event: FocusEvent): void {
    setTimeout(() => {
      const element = event.target as HTMLElement;
      element.scrollIntoView({
        behavior: 'smooth',
        block: 'center'
      });
      window.scrollBy(0, -50);
    }, 150);
  }

  // Reserva el aula para la fecha seleccionada
  reservarAula(idAula: number): void {
    const fecha = this.fechasSeleccionadas[idAula];

    if (!fecha) {
      Swal.fire('Fecha no seleccionada', 'Por favor, selecciona una fecha antes de reservar.', 'warning');
      return;
    }

    if (this.estaOcupada(idAula, fecha)) {
      Swal.fire('Fecha ocupada', 'Esta fecha ya está ocupada para el aula seleccionada.', 'warning');
      return;
    }

    const payload = {
      idUsuario: this.idUsuario,
      idAula: idAula,
      fechaReservaAula: fecha
    };

    Swal.fire({
      title: 'Reservando aula...',
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading()
    });

    this.reservaAulaService.crearReserva(payload).subscribe({
      next: () => {
        const fechaFormateada = new Intl.DateTimeFormat('es-ES').format(new Date(fecha));
        Swal.fire('¡Reservada!', `Aula reservada para el día ${fechaFormateada}`, 'success');

        // Recargar fechas ocupadas
        this.cargarFechasOcupadas(idAula);

        // Limpiar campo después de reservar
        setTimeout(() => {
          this.fechasSeleccionadas[idAula] = '';
        }, 100);
      },
      error: (err) => {
        console.error('Error al reservar aula:', err);
        Swal.fire('Error', 'No se pudo completar la reserva. Intenta con otra fecha.', 'error');
      }
    });
  }
}
