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

  private getAuthHeaders(): HttpHeaders {
    const token = this.tokenService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  obtenerLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/listar`);
  }

  obtenerLibro(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.apiUrl}/ver/${id}`);
  }

  obtenerReservas(): Observable<ReservaLibro[]> {
  return this.http.get<ReservaLibro[]>(
    `http://localhost:8080/reservas-libros/listar`,
    { headers: this.getAuthHeaders() }
  );
}

  crearLibro(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(`${this.apiUrl}/crear`, libro, {
      headers: this.getAuthHeaders()
    });
  }

  actualizarLibro(id: number, libro: Libro): Observable<Libro> {
    return this.http.put<Libro>(`${this.apiUrl}/actualizar/${id}`, libro, {
      headers: this.getAuthHeaders()
    });
  }

obtenerUltimosLibros(): Observable<Libro[]> {
  return this.http.get<Libro[]>(`${this.apiUrl}/ultimos`, {
    headers: this.getAuthHeaders()
  });
}



  eliminarLibro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  cancelarReserva(id: number): Observable<void> {
    return this.http.delete<void>(
      `http://localhost:8080/reservas-libros/borrar/${id}`,
      { headers: this.getAuthHeaders() }
    );
  }

  obtenerLibrosPopulares(): Observable<Libro[]> {
  return this.http.get<Libro[]>(`${this.apiUrl}/populares`, {
    headers: this.getAuthHeaders()
  });
}


}
