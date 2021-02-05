import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorPlagiarisedDialogFinalComponent } from './editor-plagiarised-dialog-final.component';

describe('EditorPlagiarisedDialogFinalComponent', () => {
  let component: EditorPlagiarisedDialogFinalComponent;
  let fixture: ComponentFixture<EditorPlagiarisedDialogFinalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorPlagiarisedDialogFinalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorPlagiarisedDialogFinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
