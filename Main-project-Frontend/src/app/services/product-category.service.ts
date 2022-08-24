import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProductCategory } from '../models/productCategory.model';
import { APIbaseURL } from '../settings';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductCategoryService {
  constructor(
    private http: HttpClient,
    private route: ActivatedRoute
  ) { }

  private baseURL = APIbaseURL;
  selCatName = '';



  getAllCategories(): Observable<ProductCategory[]> {
    return this.http.get<ProductCategory[]>(`${this.baseURL}/categories`);
  }

  addCategory(data: ProductCategory): Observable<ProductCategory> {
    return this.http.post<ProductCategory>(`${this.baseURL}/categories`, data);
  }

  updateCategory(id: number, data: ProductCategory): Observable<ProductCategory> {
    return this.http.put<ProductCategory>(`${this.baseURL}/categories/${id}`, data);
  }

  deleteCategory(id: number): Observable<ProductCategory> {
    return this.http.delete<ProductCategory>(`${this.baseURL}/categories/${id}`);
  }

  selectedCategoryName(stri: string) {
    this.selCatName = stri;
    console.log('serviciu lucreaza ' + this.selCatName);
  }

  getSelectedCategoryName(): Observable<string> {
    return of(this.selCatName);
  }

  getTopCategories(): Observable<ProductCategory[]> {
    return this.http.get<ProductCategory[]>(`${this.baseURL}/categories/getTopCategories`);
  }
}
