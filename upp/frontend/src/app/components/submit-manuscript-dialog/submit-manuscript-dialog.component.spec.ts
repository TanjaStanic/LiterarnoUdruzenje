import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitManuscriptDialogComponent } from './submit-manuscript-dialog.component';

describe('SubmitManuscriptDialogComponent', () => {
  let component: SubmitManuscriptDialogComponent;
  let fixture: ComponentFixture<SubmitManuscriptDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitManuscriptDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitManuscriptDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
