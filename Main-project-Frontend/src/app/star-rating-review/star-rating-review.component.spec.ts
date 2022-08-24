import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StarRatingReviewComponent } from './star-rating-review.component';

describe('StarRatingReviewComponent', () => {
  let component: StarRatingReviewComponent;
  let fixture: ComponentFixture<StarRatingReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StarRatingReviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StarRatingReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
