import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { ProductSubCategory } from '../models/productSubCategory.model';
import { APIbaseURL } from '../settings';

@Injectable({
  providedIn: 'root'
})
export class ProductSubCategoryService {

  constructor(private http: HttpClient) { }

  private baseURL = APIbaseURL;

  getAll(): Observable<ProductSubCategory[]> {
    return this.http.get<ProductSubCategory[]>(`${this.baseURL}/subcategories`);
  }

  create(data: ProductSubCategory): Observable<ProductSubCategory> {
    return this.http.post<ProductSubCategory>(`${this.baseURL}/subcategories`, data);
  }

  update(id: string | number, data: ProductSubCategory): Observable<ProductSubCategory> {
    return this.http.put<ProductSubCategory>(`${this.baseURL}/subcategories/${id}`, data);
  }

  delete(id: string | number | undefined): Observable<ProductSubCategory> {
    if (!id) {
      return of();
    }
    return this.http.delete<ProductSubCategory>(`${this.baseURL}/subcategories/${id}`);
  }
}
