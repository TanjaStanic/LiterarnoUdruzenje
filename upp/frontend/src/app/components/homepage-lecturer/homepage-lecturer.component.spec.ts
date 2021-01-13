import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageLecturerComponent } from './homepage-lecturer.component';

describe('HomepageLecturerComponent', () => {
  let component: HomepageLecturerComponent;
  let fixture: ComponentFixture<HomepageLecturerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomepageLecturerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomepageLecturerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
