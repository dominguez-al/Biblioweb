import { Component, HostListener } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

import { LoginModalComponent } from '../../modals/login-modal/login-modal.component';
import { RegistroModalComponent } from '../../modals/registro-modal/registro-modal.component';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  menuAbierto = false;

  constructor(
    private dialog: MatDialog,
    public tokenService: TokenService,
    private router: Router
  ) {}

  // Abre modal de inicio de sesión
  abrirLogin(): void {
    this.dialog.open(LoginModalComponent, {
      width: '500px',
      panelClass: 'login-dialog'
    });
  }

  // Abre modal de registro
  abrirRegistro(): void {
    this.dialog.open(RegistroModalComponent, {
      width: '500px'
    });
  }

  // Alterna visibilidad del menú de usuario
  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }

  // Cierra sesión y redirige a inicio
  cerrarSesion(): void {
    this.tokenService.logout();
    this.menuAbierto = false;
    this.router.navigate(['/']);
  }

  // Cierra el menú si se hace clic fuera del avatar
  @HostListener('document:click', ['$event'])
  handleClickFuera(event: Event): void {
    const target = event.target as HTMLElement;
    const clickDentro = target.closest('.avatar-wrapper');
    if (!clickDentro) {
      this.menuAbierto = false;
    }
  }
}
