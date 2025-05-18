import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenarLibroComponent } from './resenar-libro.component';

describe('ResenarLibroComponent', () => {
  let component: ResenarLibroComponent;
  let fixture: ComponentFixture<ResenarLibroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResenarLibroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResenarLibroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
