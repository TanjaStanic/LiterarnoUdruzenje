import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseEditorsComponent } from './choose-editors.component';

describe('ChooseEditorsComponent', () => {
  let component: ChooseEditorsComponent;
  let fixture: ComponentFixture<ChooseEditorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseEditorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseEditorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
