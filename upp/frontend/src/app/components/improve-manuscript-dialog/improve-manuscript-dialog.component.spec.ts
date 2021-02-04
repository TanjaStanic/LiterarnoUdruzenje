import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImproveManuscriptDialogComponent } from './improve-manuscript-dialog.component';

describe('ImproveManuscriptDialogComponent', () => {
  let component: ImproveManuscriptDialogComponent;
  let fixture: ComponentFixture<ImproveManuscriptDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImproveManuscriptDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImproveManuscriptDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
