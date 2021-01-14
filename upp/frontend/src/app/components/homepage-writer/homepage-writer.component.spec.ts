import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageWriterComponent } from './homepage-writer.component';

describe('HomepageWriterComponent', () => {
  let component: HomepageWriterComponent;
  let fixture: ComponentFixture<HomepageWriterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomepageWriterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomepageWriterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
