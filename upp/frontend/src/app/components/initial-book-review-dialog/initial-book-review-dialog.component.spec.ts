import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialBookReviewDialogComponent } from './initial-book-review-dialog.component';

describe('InitialBookReviewDialogComponent', () => {
  let component: InitialBookReviewDialogComponent;
  let fixture: ComponentFixture<InitialBookReviewDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InitialBookReviewDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InitialBookReviewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
