import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizeBrowseComponent } from './visualize-browse.component';

describe('VisualizeBrowseComponent', () => {
  let component: VisualizeBrowseComponent;
  let fixture: ComponentFixture<VisualizeBrowseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizeBrowseComponent]
    });
    fixture = TestBed.createComponent(VisualizeBrowseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
