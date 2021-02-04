import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BetaReaderCommentDialogComponent } from './beta-reader-comment-dialog.component';

describe('BetaReaderCommentDialogComponent', () => {
  let component: BetaReaderCommentDialogComponent;
  let fixture: ComponentFixture<BetaReaderCommentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BetaReaderCommentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BetaReaderCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
