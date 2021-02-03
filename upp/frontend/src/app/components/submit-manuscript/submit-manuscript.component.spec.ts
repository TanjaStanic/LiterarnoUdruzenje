import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitManuscriptComponent } from './submit-manuscript.component';

describe('SubmitManuscriptComponent', () => {
  let component: SubmitManuscriptComponent;
  let fixture: ComponentFixture<SubmitManuscriptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitManuscriptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitManuscriptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
