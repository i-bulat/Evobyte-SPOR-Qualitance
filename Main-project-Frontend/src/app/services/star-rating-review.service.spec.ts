import { TestBed } from '@angular/core/testing';

import { StarRatingReviewService } from './star-rating-review.service';

describe('StarRatingReviewService', () => {
  let service: StarRatingReviewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StarRatingReviewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
