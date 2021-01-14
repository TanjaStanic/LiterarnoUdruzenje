import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageReaderComponent } from './homepage-reader.component';

describe('HomepageReaderComponent', () => {
  let component: HomepageReaderComponent;
  let fixture: ComponentFixture<HomepageReaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomepageReaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomepageReaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
