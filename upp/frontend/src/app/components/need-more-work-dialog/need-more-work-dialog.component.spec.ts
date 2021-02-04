import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedMoreWorkDialogComponent } from './need-more-work-dialog.component';

describe('NeedMoreWorkDialogComponent', () => {
  let component: NeedMoreWorkDialogComponent;
  let fixture: ComponentFixture<NeedMoreWorkDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeedMoreWorkDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedMoreWorkDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
