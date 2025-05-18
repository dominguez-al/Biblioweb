import { TestBed } from '@angular/core/testing';

import { ReservaAulaService } from './reserva-aula.service';

describe('ReservaAulaService', () => {
  let service: ReservaAulaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservaAulaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
