import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { Product } from 'src/app/models/product.model';
import { StarRatingReviewComponent } from 'src/app/star-rating-review/star-rating-review.component';
import { StarRatingReviewService } from 'src/app/services/star-rating-review.service';
import { AuthenticationService } from 'src/app/services/authentication.service';


@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.scss']
})
export class ProductPageComponent implements OnInit {

  product?: Product;
  found: boolean = false;
  items: Product[] = [];
  starRating = 0;
  reviews: any[] = [];
  quantity = 1;
  id = +this.route.snapshot.paramMap.get('productId')!;




  constructor(

    private route: ActivatedRoute,
    private cartService: CartService,
    private starRatingReview: StarRatingReviewService,
    private authenticationService: AuthenticationService) { }

  ngOnInit(): void {

    const id = +this.route.snapshot.paramMap.get('productId')!;
    //console.log(productIdFromRoute);
    this.starRatingReview.getAll(id).subscribe((data: any) => {
      this.reviews = data;
      console.log(this.reviews);
      this.starReviewAverage();
    });

    this.cartService.getProduct(id).subscribe((data: Product) => {
      console.log(data);
      this.product = data;
    });


  }


  isAutethicated() {

    return this.authenticationService.isAuthenticated();

  }

  starReviewAverage() {


    this.starRating = 0;
    this.reviews.forEach(element => {

      this.starRating += element.rating;
    });

    this.starRating = this.starRating / this.reviews.length;

  }

  addtoCart(id: number, quantity: number) {
    this.cartService.addToBasket(id, quantity).subscribe(res => {
      this.cartService.setProduct(res);
    });
  }

}
