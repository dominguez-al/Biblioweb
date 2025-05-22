import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UsuarioRegistro {
  nombre: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  // Inicia sesión con email y contraseña, devuelve un token JWT
  login(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, { email, password });
  }

  // Registra un nuevo usuario en el sistema
  registrar(usuario: UsuarioRegistro): Observable<string> {
    return this.http.post(`${this.baseUrl}/registro`, usuario, {
      responseType: 'text'
    });
  }
}
