import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorPlagiarisedDialogComponent } from './editor-plagiarised-dialog.component';

describe('EditorPlagiarisedDialogComponent', () => {
  let component: EditorPlagiarisedDialogComponent;
  let fixture: ComponentFixture<EditorPlagiarisedDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorPlagiarisedDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorPlagiarisedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
