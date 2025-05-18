import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

interface TokenPayload {
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
  getPayload(): TokenPayload | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      return jwtDecode<TokenPayload>(token);
    } catch {
      return null;
    }
  }

  // Comprobar si el usuario es ADMIN
  isAdmin(): boolean {
    return this.getPayload()?.role === 'ADMIN';
  }

  // Devuelve el ID del usuario
  getUserId(): number | null {
    const payload = this.getPayload();
    console.log('Payload decodificado:', payload);
    return payload?.idUsuario || null;
  }

  // Devuelve el rol
  getRole(): string | null {
    return this.getPayload()?.role || null;
  }

  // Devuelve el nombre
  getNombre(): string | null {
    return this.getPayload()?.nombre || null;
  }

  // Devuelve el email
  getEmail(): string | null {
    return this.getPayload()?.sub || null;
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
