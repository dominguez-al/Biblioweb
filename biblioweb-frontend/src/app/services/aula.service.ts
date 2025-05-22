import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AulaService {
  private apiUrl = 'http://localhost:8080/aulas/listar';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  // Genera los headers con el token JWT para autenticación
  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Solicita la lista de aulas al backend con autenticación
  obtenerAulas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, {
      headers: this.getAuthHeaders()
    });
  }
}
