import { TestBed } from '@angular/core/testing';

import { ResenaService } from './resenar.service';

describe('ResenaService', () => {
  let service: ResenaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResenaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
