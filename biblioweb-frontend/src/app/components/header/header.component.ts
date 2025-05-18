import { Component, HostListener } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoginModalComponent } from '../../modals/login-modal/login-modal.component';
import { RegistroModalComponent } from '../../modals/registro-modal/registro-modal.component';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';

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

  abrirLogin() {
    this.dialog.open(LoginModalComponent, {
      width: '500px',
      panelClass: 'login-dialog'
    });
  }

  abrirRegistro() {
    this.dialog.open(RegistroModalComponent, {
      width: '500px'
    });
  }

  toggleMenu() {
    this.menuAbierto = !this.menuAbierto;
  }

  cerrarSesion() {
    this.tokenService.logout();
    this.menuAbierto = false;
    this.router.navigate(['/']); // También podrías usar Router.navigate(['/']);
  }

  @HostListener('document:click', ['$event'])
  handleClickFuera(event: Event) {
    const target = event.target as HTMLElement;
    const clickDentro = target.closest('.avatar-wrapper');
    if (!clickDentro) {
      this.menuAbierto = false;
    }
  }
}
