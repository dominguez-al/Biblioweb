import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

export interface LibroPopular {
  idLibro: number;
  titulo: string;
  imagen: string;
  totalReservas: number;
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioLibroService {
  private apiUrl = 'http://localhost:8080/reservas-libros';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  obtenerLibrosMasPopulares(): Observable<LibroPopular[]> {
    return this.http.get<LibroPopular[]>(`${this.apiUrl}/populares`, {
      headers: this.getAuthHeaders()
    });
  }
}
