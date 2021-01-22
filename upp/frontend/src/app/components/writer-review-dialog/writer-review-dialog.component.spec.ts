import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterReviewDialogComponent } from './writer-review-dialog.component';

describe('WriterReviewDialogComponent', () => {
  let component: WriterReviewDialogComponent;
  let fixture: ComponentFixture<WriterReviewDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterReviewDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterReviewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
