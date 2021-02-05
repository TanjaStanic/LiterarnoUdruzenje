import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorPlagiarisedTableComponent } from './editor-plagiarised-table.component';

describe('EditorPlagiarisedTableComponent', () => {
  let component: EditorPlagiarisedTableComponent;
  let fixture: ComponentFixture<EditorPlagiarisedTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorPlagiarisedTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorPlagiarisedTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
