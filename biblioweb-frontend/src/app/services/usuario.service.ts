import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

export interface Usuario {
  id?: number;
  nombre: string;
  email: string;
  rol: 'USER' | 'ADMIN' | '';
  password?: string;
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/usuarios';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  // Retorna los headers con token JWT para autenticación
  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Obtiene la lista de todos los usuarios
  obtenerUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/listar`, {
      headers: this.getAuthHeaders()
    });
  }

  // Crea un nuevo usuario en el sistema
  crearUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/crear`, usuario, {
      headers: this.getAuthHeaders()
    });
  }

  // Actualiza los datos de un usuario existente
  actualizarUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/actualizar/${id}`, usuario, {
      headers: this.getAuthHeaders()
    });
  }

  // Elimina un usuario por ID
  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, {
      headers: this.getAuthHeaders()
    });
  }
}
