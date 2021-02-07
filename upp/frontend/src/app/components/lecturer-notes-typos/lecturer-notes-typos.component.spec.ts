import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LecturerNotesTyposComponent } from './lecturer-notes-typos.component';

describe('LecturerNotesTyposComponent', () => {
  let component: LecturerNotesTyposComponent;
  let fixture: ComponentFixture<LecturerNotesTyposComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LecturerNotesTyposComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LecturerNotesTyposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
