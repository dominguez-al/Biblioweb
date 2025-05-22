import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ReservaAulaService {
  private apiUrl = 'http://localhost:8080/reservas-aulas';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  // Genera los headers con el token para autenticación
  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Crear una nueva reserva de aula
  crearReserva(payload: { idUsuario: number; idAula: number; fechaReservaAula: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, payload, {
      headers: this.getAuthHeaders()
    });
  }

  // Obtener reservas del usuario logueado
  obtenerReservasUsuario(idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/mis-reservas`, {
      params: { idUsuario },
      headers: this.getAuthHeaders()
    });
  }

  // Obtener las fechas ocupadas para un aula específica
  obtenerFechasOcupadas(idAula: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/fechas-ocupadas/${idAula}`, {
      headers: this.getAuthHeaders()
    });
  }

  // Cancelar una reserva de aula por ID
  cancelarReserva(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/borrar/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  // Obtener todas las reservas (admin)
  obtenerTodasLasReservas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listar`, {
      headers: this.getAuthHeaders()
    });
  }
}
