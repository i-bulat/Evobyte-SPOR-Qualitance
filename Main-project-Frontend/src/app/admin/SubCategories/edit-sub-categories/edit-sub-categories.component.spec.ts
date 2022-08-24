import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubCategoriesComponent } from './edit-sub-categories.component';

describe('EditSubCategoriesComponent', () => {
  let component: EditSubCategoriesComponent;
  let fixture: ComponentFixture<EditSubCategoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSubCategoriesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSubCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
