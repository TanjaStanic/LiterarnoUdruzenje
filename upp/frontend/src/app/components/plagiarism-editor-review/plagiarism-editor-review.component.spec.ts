import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlagiarismEditorReviewComponent } from './plagiarism-editor-review.component';

describe('PlagiarismEditorReviewComponent', () => {
  let component: PlagiarismEditorReviewComponent;
  let fixture: ComponentFixture<PlagiarismEditorReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlagiarismEditorReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlagiarismEditorReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
