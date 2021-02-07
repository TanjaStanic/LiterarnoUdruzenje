import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericInputRowComponent } from './generic-input-row.component';

describe('GenericInputRowComponent', () => {
  let component: GenericInputRowComponent;
  let fixture: ComponentFixture<GenericInputRowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenericInputRowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericInputRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
