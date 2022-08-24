import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { APIbaseURL } from '../settings';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class StarRatingReviewService {

  constructor(private http: HttpClient, private router: ActivatedRoute) { }



  private baseURL = APIbaseURL;


  create(id: number, data: any): Observable<any> {
    data.product = { id: id };
    return this.http.post(`${this.baseURL}/products/addReview/${id}`, data);
  }

  getAll(productId: number): Observable<any> {
    return this.http.get(`${this.baseURL}/products/getReviews/${productId}`);
  }
}


