import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioLogueadoComponent } from './usuario-logueado.component';

describe('UsuarioLogueadoComponent', () => {
  let component: UsuarioLogueadoComponent;
  let fixture: ComponentFixture<UsuarioLogueadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuarioLogueadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioLogueadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
