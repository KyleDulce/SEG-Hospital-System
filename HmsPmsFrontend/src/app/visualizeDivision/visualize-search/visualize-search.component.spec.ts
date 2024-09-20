import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizeSearchComponent } from './visualize-search.component';

describe('VisualizeSearchComponent', () => {
  let component: VisualizeSearchComponent;
  let fixture: ComponentFixture<VisualizeSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizeSearchComponent]
    });
    fixture = TestBed.createComponent(VisualizeSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
