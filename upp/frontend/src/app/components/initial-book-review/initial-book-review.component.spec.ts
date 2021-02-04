import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialBookReviewComponent } from './initial-book-review.component';

describe('InitialBookReviewComponent', () => {
  let component: InitialBookReviewComponent;
  let fixture: ComponentFixture<InitialBookReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InitialBookReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InitialBookReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
