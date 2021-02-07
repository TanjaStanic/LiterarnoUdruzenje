import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterComplaintComponent } from './writer-complaint.component';

describe('WriterComplaintComponent', () => {
  let component: WriterComplaintComponent;
  let fixture: ComponentFixture<WriterComplaintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterComplaintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
