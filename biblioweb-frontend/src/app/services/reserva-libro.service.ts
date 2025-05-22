import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

export interface Reserva {
  id: number;
  libro: {
    idLibro: number;
    titulo: string;
    autor: string;
    genero: string;
    estado: string;
    imagen: string;
    fechaAltaLibro: string;
  };
  reservadoPor: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReservaLibroService {
  private apiUrl = 'http://localhost:8080/reservas-libros';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  // Retorna los headers necesarios con el token JWT
  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Realiza una reserva de libro
  reservar(payload: { idUsuario: number; idLibro: number }): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, payload, {
      headers: this.getAuthHeaders()
    });
  }

  // Obtiene las reservas realizadas por un usuario
  obtenerReservasUsuario(idUsuario: number): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.apiUrl}/mis-reservas`, {
      params: { idUsuario },
      headers: this.getAuthHeaders()
    });
  }

  // Cancela una reserva de libro por su ID
  cancelarReserva(idReserva: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${idReserva}`, {
      headers: this.getAuthHeaders()
    });
  }
}
