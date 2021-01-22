import { TestBed } from '@angular/core/testing';

import { RegistrationApplicationResponseService } from './registration-application-response.service';

describe('RegistrationApplicationResponseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RegistrationApplicationResponseService = TestBed.get(RegistrationApplicationResponseService);
    expect(service).toBeTruthy();
  });
});
