import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorHaveSuggestionDialogComponent } from './editor-have-suggestion-dialog.component';

describe('EditorHaveSuggestionDialogComponent', () => {
  let component: EditorHaveSuggestionDialogComponent;
  let fixture: ComponentFixture<EditorHaveSuggestionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorHaveSuggestionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorHaveSuggestionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
