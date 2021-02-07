import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookReviewDialogBetaReadersComponent } from './book-review-dialog-beta-readers.component';

describe('BookReviewDialogBetaReadersComponent', () => {
  let component: BookReviewDialogBetaReadersComponent;
  let fixture: ComponentFixture<BookReviewDialogBetaReadersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookReviewDialogBetaReadersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookReviewDialogBetaReadersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
