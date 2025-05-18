import { TestBed } from '@angular/core/testing';

import { ReservaLibroService } from './reserva-libro.service';

describe('ReservaLibroService', () => {
  let service: ReservaLibroService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservaLibroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
