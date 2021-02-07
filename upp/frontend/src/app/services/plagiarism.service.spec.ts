import { TestBed } from '@angular/core/testing';

import { PlagiarismService } from './plagiarism.service';

describe('PlagiarismService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PlagiarismService = TestBed.get(PlagiarismService);
    expect(service).toBeTruthy();
  });
});
