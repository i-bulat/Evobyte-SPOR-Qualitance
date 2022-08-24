import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryAndProductDisplayComponent } from './category-and-product-display.component';

describe('CategoryAndProductDisplayComponent', () => {
  let component: CategoryAndProductDisplayComponent;
  let fixture: ComponentFixture<CategoryAndProductDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryAndProductDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryAndProductDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
