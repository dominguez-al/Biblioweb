import { RegistroModalComponent } from './modals/registro-modal/registro-modal.component';
import { LoginModalComponent } from './modals/login-modal/login-modal.component';
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CatalogoComponent } from './pages/catalogo/catalogo.component';
import { EventosComponent } from './pages/eventos/eventos.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  // { path: 'login', component: LoginModalComponent },
  // { path: 'registro', component: RegistroModalComponent },
  { path: 'catalogo', component: CatalogoComponent },
  { path: 'eventos', component: EventosComponent},
  { path: '**', redirectTo: '' },  // Redirige cualquier ruta desconocida al Home

];
