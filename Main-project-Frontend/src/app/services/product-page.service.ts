import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APIbaseURL } from '../settings';

@Injectable({
  providedIn: 'root'
})
export class ProductPageService {

  constructor( private http: HttpClient ) { }

  private baseURL = `${APIbaseURL}/products/ProductPage`;

  getProductsAndPage(subCategoryId: number | any, page: number): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/${subCategoryId}/${page}`);
  }
  
}