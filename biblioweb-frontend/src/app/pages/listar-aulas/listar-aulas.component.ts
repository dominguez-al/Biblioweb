import { Component, OnInit } from '@angular/core';
import { AulaService } from '../../services/aula.service';
import { TokenService } from '../../services/token.service';
import { ReservaAulaService } from '../../services/reserva-aula.service';

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
    // Obtener ID del usuario autenticado
    const payload = this.tokenService.getPayload();
    if (payload?.idUsuario) {
      this.idUsuario = payload.idUsuario;
      console.log("🧪 ID de usuario:", this.idUsuario);
    } else {
      console.error("❌ No se pudo obtener el ID del usuario del token");
    }

    // Cargar aulas y sus fechas ocupadas
    this.aulaService.obtenerAulas().subscribe(aulas => {
      this.aulas = aulas;
      console.log('📋 Aulas cargadas:', this.aulas);

      this.aulas.forEach(aula => {
        this.cargarFechasOcupadas(aula.id);
      });
    });
  }

  // Clase CSS según estado del aula
  getClaseEstado(estado: string): string {
    return (estado || '').trim().toLowerCase();
  }

  // Verifica si una fecha concreta ya está ocupada para un aula
  estaOcupada(idAula: number, fecha: string): boolean {
    return this.fechasOcupadas[idAula]?.includes(fecha);
  }

  // Carga fechas ocupadas de un aula desde el backend
  cargarFechasOcupadas(idAula: number) {
    this.reservaAulaService.obtenerFechasOcupadas(idAula).subscribe(fechas => {
      this.fechasOcupadas[idAula] = fechas.map(f => f.toString());
    });
  }

  // Lógica de selección de fecha
  onFechaSeleccionada(fecha: string, idAula: number): void {
    this.fechasSeleccionadas[idAula] = fecha;
  }

  // Intenta reservar un aula en una fecha concreta
  reservarAula(idAula: number) {
    const fecha = this.fechasSeleccionadas[idAula];
    if (!fecha) {
      alert("Por favor, selecciona una fecha.");
      return;
    }

    if (this.estaOcupada(idAula, fecha)) {
      alert("⚠️ Esta fecha ya está ocupada para el aula seleccionada.");
      return;
    }

    const payload = {
      idUsuario: this.idUsuario,
      idAula: idAula,
      fechaReservaAula: fecha
    };

    console.log("📦 Payload enviado:", payload);

    this.reservaAulaService.crearReserva(payload).subscribe({
      next: (res: any) => {
        console.log("✅ Reserva creada", res);
        const fechaFormateada = new Intl.DateTimeFormat('es-ES').format(new Date(fecha));
        alert("✅ Aula reservada correctamente para el día " + fechaFormateada);

        // Volver a cargar fechas ocupadas tras reservar
        this.cargarFechasOcupadas(idAula);

        // Limpia la fecha tras breve retraso para que se renderice bien
        setTimeout(() => {
          this.fechasSeleccionadas[idAula] = '';
        }, 100);
      },
      error: (err: any) => {
        console.error("❌ Error al reservar aula:", err);
        alert("❌ Error al reservar el aula. Intenta nuevamente con otro día.");
      }
    });
  }
}
