import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Location } from '@angular/common';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { StarRatingReviewService } from '../services/star-rating-review.service';

@Component({
  selector: 'app-star-rating-review',
  templateUrl: './star-rating-review.component.html',
  styleUrls: ['./star-rating-review.component.scss']
})
export class StarRatingReviewComponent implements OnInit {


  id = +this.router.snapshot.paramMap.get('productId')!;

  starRating = 0;

  constructor(public fb: FormBuilder,
    private router: ActivatedRoute,
    public userService: UserService,
    private location: Location,
    private route: Router,
    private starRatingReview: StarRatingReviewService) { }

  productForm = this.fb.group({

    rating: ['', Validators.required],
    title: [''],
    comment: [''],


  });



  ngOnInit(): void {

    const id1 = +this.router.snapshot.paramMap.get('productId')!;
    //console.log(id1);
    console.log(this.id);



  }


  submitForm() {

    console.log(this.productForm.value);

    this.starRatingReview.create(this.id, this.productForm.value).subscribe((data: any) => {

      console.log('Review Created!');
      this.route.navigateByUrl(`products/${this.id}`);

    });
  }

  back(event: any): void {
    event.preventDefault()
    this.location.back()
  }
}
