import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageEditorComponent } from './homepage-editor.component';

describe('HomepageEditorComponent', () => {
  let component: HomepageEditorComponent;
  let fixture: ComponentFixture<HomepageEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomepageEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomepageEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
