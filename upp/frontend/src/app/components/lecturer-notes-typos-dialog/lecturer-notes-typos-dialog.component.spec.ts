import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LecturerNotesTyposDialogComponent } from './lecturer-notes-typos-dialog.component';

describe('LecturerNotesTyposDialogComponent', () => {
  let component: LecturerNotesTyposDialogComponent;
  let fixture: ComponentFixture<LecturerNotesTyposDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LecturerNotesTyposDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LecturerNotesTyposDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
