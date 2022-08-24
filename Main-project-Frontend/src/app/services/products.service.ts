import { asNativeElements, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { APIbaseURL } from '../settings';
import { Product } from '../models/product.model';
import { ProductCategory } from '../models/productCategory.model';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  constructor(private http: HttpClient) { }

  private baseURL = `${APIbaseURL}/products`;
  private subCategoryURL = `${APIbaseURL}/subcategories`;

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseURL}`);
  }

  getById(id: string | number): Observable<Product> {
    return this.http.get<Product>(`${this.baseURL}/${id}`);
  }

  create(data: Product): Observable<Product> {
    return this.http.post<Product>(`${this.baseURL}`, data);
  }

  update(id: string | number, data: Product): Observable<Product> {
    return this.http.put<Product>(`${this.baseURL}/${id}`, data);
  }

  delete(id: string | number | undefined): Observable<Product> {
    if (!id) {
      return of();
    }
    return this.http.delete<Product>(`${this.baseURL}/${id}`);
  }

  getAllSubCategory(): Observable<any> {
    return this.http.get<any>(`${this.subCategoryURL}`);
  }

  getSearchSuggestions(s: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseURL}/searchBar/${s}`);
  }

  getSearchResults(s: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseURL}/productsSearched/${s}`);
  }

  getTopProducts(category: number) {
    return this.http.get<Product[]>(`${this.baseURL}/getTopProducts/${category}`)
  }
}
