import { TestBed } from '@angular/core/testing';

import { LineitemService } from './lineitem.service';

describe('LineitemService', () => {
  let service: LineitemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LineitemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
