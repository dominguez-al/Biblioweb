import { Component } from '@angular/core';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-usuario-logueado',
  standalone: false,
  templateUrl: './usuario-logueado.component.html',
  styleUrls: ['./usuario-logueado.component.scss']
})
export class UsuarioLogueadoComponent {

  constructor(public tokenService: TokenService) {
    // Mostrar el rol del usuario por consola (útil para debug)
    console.log('Rol del usuario:', this.tokenService.getRole());
  }

  // Verifica si el usuario es administrador
  esAdmin(): boolean {
    return this.tokenService.getRole() === 'ADMIN';
  }

  // Verifica si el usuario es un usuario común
  esUsuario(): boolean {
    return this.tokenService.getRole() === 'USER';
  }
}
