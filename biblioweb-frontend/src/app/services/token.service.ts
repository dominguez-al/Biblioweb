import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

interface UsuarioToken {
  sub: string;         // Email del usuario
  role: string;        // 'USER' o 'ADMIN'
  nombre: string;      // Nombre del usuario
  idUsuario: number;   // ID del usuario
  exp: number;         // Expiración del token (opcional)
}

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  // Obtiene el token del localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Decodifica el token JWT
  getUsuarioDesdeToken(): UsuarioToken | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      return jwtDecode<UsuarioToken>(token);
    } catch {
      return null;
    }
  }

  // Comprobar si el usuario es ADMIN
  isAdmin(): boolean {
    return this.getUsuarioDesdeToken()?.role === 'ADMIN';
  }

  // Devuelve el ID del usuario
  getUsuarioId(): number | null {
    const datosUsuario  = this.getUsuarioDesdeToken();
    console.log('datosUsuario  decodificado:', datosUsuario );
    return datosUsuario ?.idUsuario || null;
  }

  // Devuelve el rol
  getRole(): string | null {
    return this.getUsuarioDesdeToken()?.role || null;
  }

  // Devuelve el nombre
  getNombre(): string | null {
    return this.getUsuarioDesdeToken()?.nombre || null;
  }

  // Devuelve el email
  getEmail(): string | null {
    return this.getUsuarioDesdeToken()?.sub || null;
  }

  // Comprueba si hay sesión iniciada
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Elimina el token del localStorage
  logout(): void {
    localStorage.removeItem('token');
  }
}
