import { UsuarioLogueadoComponent } from './pages/usuario-logueado/usuario-logueado.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { Import, LucideAngularModule, UserPlus, UserPen, CircleArrowLeft, LibraryBig, Shield , UserSearch, NotebookPen, PenTool, CalendarDays, OctagonX , CircleUserRound, House, BookCheck, Puzzle, User, LogOut, Book, Calculator, FolderKanban, BookPlus , BookOpenText , BookKey, Table, List   } from 'lucide-angular';
import { Eye, EyeOff } from 'lucide';
import { MatDialogModule } from '@angular/material/dialog';
import { HomeComponent } from './pages/home/home.component';
import { CatalogoComponent } from './pages/catalogo/catalogo.component';
import { EventosComponent } from './pages/eventos/eventos.component';
import {HeaderComponent} from './components/header/header.component';
import { RegistroModalComponent } from './modals/registro-modal/registro-modal.component';
import { LoginModalComponent } from './modals/login-modal/login-modal.component';
import { AppRoutingModule } from './app-routing.module';
import { CommonModule } from '@angular/common';
import { MisReservasComponent } from './pages/mis-reservas/mis-reservas.component';
import { ResenarLibroComponent } from './pages/resenar-libro/resenar-libro.component';
import { LibroDetalleModalComponent } from './modals/libro-detalle-modal/libro-detalle-modal.component';
import { ListarAulasComponent } from './pages/listar-aulas/listar-aulas.component';
import { AdminLibrosComponent } from './pages/admin-libros/admin-libros.component';
import { AdminAulasComponent } from './pages/admin-aulas/admin-aulas.component';
import { AdminUsuariosComponent } from './pages/admin-usuarios/admin-usuarios.component';




@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CatalogoComponent,
    EventosComponent,
    HeaderComponent,
    RegistroModalComponent,
    LoginModalComponent,
    MisReservasComponent,
    ResenarLibroComponent,
    UsuarioLogueadoComponent,
    LibroDetalleModalComponent,
    ListarAulasComponent,
    AdminLibrosComponent,
    AdminAulasComponent,
    AdminUsuariosComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatDialogModule,
    LucideAngularModule.pick({ Eye, EyeOff, UserPlus, UserPen, CircleArrowLeft, LibraryBig, Shield , UserSearch, NotebookPen,PenTool,CalendarDays, OctagonX, CircleUserRound, House, BookCheck, Puzzle, User, LogOut, Book, Calculator, FolderKanban , BookPlus , BookOpenText , BookKey, Table, List  })
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
