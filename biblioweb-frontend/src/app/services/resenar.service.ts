import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ResenarService {
  private apiUrl = 'http://localhost:8080/resenas';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  crearResena(libroId: number, email: string, comentario: string, puntuacion: number): Observable<any> {
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const body = {
      libroId,
      usuarioId: email,
      comentario,
      puntuacion
    };

    return this.http.post(`${this.apiUrl}/guardarResena`, body, { headers });
  }

  obtenerTodasResenas(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/todas`);
  }

}
