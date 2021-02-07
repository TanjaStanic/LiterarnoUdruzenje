import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorPlagiarisedDialogReviewComponent } from './editor-plagiarised-dialog-review.component';

describe('EditorPlagiarisedDialogReviewComponent', () => {
  let component: EditorPlagiarisedDialogReviewComponent;
  let fixture: ComponentFixture<EditorPlagiarisedDialogReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorPlagiarisedDialogReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorPlagiarisedDialogReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
