import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterReviewComponent } from './writer-review.component';

describe('WriterReviewComponent', () => {
  let component: WriterReviewComponent;
  let fixture: ComponentFixture<WriterReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
