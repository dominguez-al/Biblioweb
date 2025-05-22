import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service'; // Asegúrate que la ruta es correcta

export interface Libro {
  idLibro?: number;
  titulo: string;
  autor: string;
  genero: string;
  estado: string;
  fechaAltaLibro: string;
  imagen: string;
  reservadoPor?: string;
  totalReservas?: number;
}

export interface ReservaLibro {
  id: number;
  idLibro: number;
  idUsuario: number;
  titulo: string;
  fechaReservaLibro: string;
  fechaDevolucion: string;
  imagen: string;
}

@Injectable({
  providedIn: 'root'
})
export class LibroService {
  private apiUrl = 'http://localhost:8080/libros';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  // Obtener headers con token JWT para autenticación
  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Obtener todos los libros disponibles
  obtenerLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/listar`);
  }

  // Obtener un libro específico por ID
  obtenerLibro(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.apiUrl}/ver/${id}`);
  }

  // Obtener todas las reservas de libros (admin)
  obtenerReservas(): Observable<ReservaLibro[]> {
    return this.http.get<ReservaLibro[]>(
      `http://localhost:8080/reservas-libros/listar`,
      { headers: this.getAuthHeaders() }
    );
  }

  // Crear un nuevo libro
  crearLibro(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(`${this.apiUrl}/crear`, libro, {
      headers: this.getAuthHeaders()
    });
  }

  // Actualizar un libro existente
  actualizarLibro(id: number, libro: Libro): Observable<Libro> {
    return this.http.put<Libro>(`${this.apiUrl}/actualizar/${id}`, libro, {
      headers: this.getAuthHeaders()
    });
  }

  // Obtener los últimos libros añadidos
  obtenerUltimosLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/ultimos`, {
      headers: this.getAuthHeaders()
    });
  }

  // Eliminar un libro por ID
  eliminarLibro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  // Cancelar una reserva de libro
  cancelarReserva(id: number): Observable<void> {
    return this.http.delete<void>(
      `http://localhost:8080/reservas-libros/borrar/${id}`,
      { headers: this.getAuthHeaders() }
    );
  }

  // Obtener libros más reservados (populares)
  obtenerLibrosPopulares(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/populares`, {
      headers: this.getAuthHeaders()
    });
  }
}
