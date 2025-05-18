import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CatalogoComponent } from './pages/catalogo/catalogo.component';
import { EventosComponent } from './pages/eventos/eventos.component';
import { UsuarioLogueadoComponent } from './pages/usuario-logueado/usuario-logueado.component';
import { ResenarLibroComponent } from './pages/resenar-libro/resenar-libro.component';
import { MisReservasComponent } from './pages/mis-reservas/mis-reservas.component';
import { ListarAulasComponent } from './pages/listar-aulas/listar-aulas.component';
import { AdminLibrosComponent } from './pages/admin-libros/admin-libros.component';
import { AdminAulasComponent } from './pages/admin-aulas/admin-aulas.component';
import { AdminUsuariosComponent } from './pages/admin-usuarios/admin-usuarios.component';



const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'catalogo', component: CatalogoComponent },
  { path: 'eventos', component: EventosComponent },
  {path: 'usuario-logueado',component: UsuarioLogueadoComponent,},
  { path: 'resenar-libro', component: ResenarLibroComponent },
  { path: 'mis-reservas', component: MisReservasComponent },
  { path: 'aulas', component: ListarAulasComponent },
  { path: 'admin-libros', component: AdminLibrosComponent },
  { path: 'admin-aulas', component: AdminAulasComponent },
  { path: 'admin-usuarios', component: AdminUsuariosComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
