import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPmDialogComponent } from './new-pm-dialog.component';

describe('NewPmDialogComponent', () => {
  let component: NewPmDialogComponent;
  let fixture: ComponentFixture<NewPmDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPmDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
