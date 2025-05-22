import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';

import Swal from 'sweetalert2';

import { Libro } from '../../services/libro.service';
import { TokenService } from '../../services/token.service';
import { ReservaLibroService } from '../../services/reserva-libro.service';
import { LoginModalComponent } from '../../modals/login-modal/login-modal.component';

@Component({
  selector: 'app-libro-detalle-modal',
  standalone: false,
  templateUrl: './libro-detalle-modal.component.html',
  styleUrls: ['./libro-detalle-modal.component.scss']
})
export class LibroDetalleModalComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public libro: Libro,
    public dialogRef: MatDialogRef<LibroDetalleModalComponent>,
    private tokenService: TokenService,
    private reservaService: ReservaLibroService,
    private dialog: MatDialog
  ) {}

  // Método para reservar el libro
  reservarLibro(): void {
    if (!this.tokenService.isLoggedIn()) {
      this.dialogRef.close();
      this.dialog.open(LoginModalComponent, {
        width: '500px',
        panelClass: 'login-dialog'
      });
      return;
    }

    const idUsuario = this.tokenService.getUsuarioId();
    const idLibro = this.libro.idLibro;

    if (!idUsuario || !idLibro) {
      Swal.fire('Datos faltantes', 'No se pudo obtener la información necesaria.', 'warning');
      return;
    }

    Swal.fire({
      title: 'Reservando...',
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading()
    });

    this.reservaService.reservar({ idUsuario, idLibro }).subscribe({
      next: () => {
        this.libro.estado = 'RESERVADO';
        Swal.fire('¡Éxito!', 'Reserva realizada correctamente.', 'success').then(() => {
          this.dialogRef.close(true);
        });
      },
      error: (err) => {
        console.error('Error al reservar libro:', err);
        Swal.fire('Error', 'No se pudo realizar la reserva.', 'error');
      }
    });
  }

  // Cierra el modal sin hacer nada
  cerrar(): void {
    this.dialogRef.close();
  }
}
