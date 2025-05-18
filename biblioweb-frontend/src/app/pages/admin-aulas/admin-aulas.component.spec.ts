import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAulasComponent } from './admin-aulas.component';

describe('AdminAulasComponent', () => {
  let component: AdminAulasComponent;
  let fixture: ComponentFixture<AdminAulasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAulasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAulasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
