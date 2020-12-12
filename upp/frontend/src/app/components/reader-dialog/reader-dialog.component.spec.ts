import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderDialogComponent } from './reader-dialog.component';

describe('ReaderDialogComponent', () => {
  let component: ReaderDialogComponent;
  let fixture: ComponentFixture<ReaderDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReaderDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReaderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
