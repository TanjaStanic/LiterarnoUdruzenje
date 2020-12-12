import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterDialogComponent } from './writer-dialog.component';

describe('WriterDialogComponent', () => {
  let component: WriterDialogComponent;
  let fixture: ComponentFixture<WriterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
