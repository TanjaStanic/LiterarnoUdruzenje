import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookReviewDialogComponent } from './book-review-dialog.component';

describe('BookReviewDialogComponent', () => {
  let component: BookReviewDialogComponent;
  let fixture: ComponentFixture<BookReviewDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookReviewDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookReviewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
