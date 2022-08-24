import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LineItem, LineItemPost } from '../models/line-item.model';
import { Order } from '../models/order.model';
import { Product } from '../models/product.model';
import { APIbaseURL } from '../settings';
import { OrdersService } from './orders.service';
import { ProductsService } from './products.service';

@Injectable({
  providedIn: 'root'
})
export class LineitemService {
  constructor(private http: HttpClient, private ordersService: OrdersService, private productsService: ProductsService) { }

  // private baseURL = APIbaseURL;
  private baseURL = `${APIbaseURL}/lineItems`;
  getAll(): Observable<LineItem[]> {
    return this.http.get<LineItem[]>(`${this.baseURL}`);
  }

  getLineItem(id: number): Observable<LineItem> {
    return this.http.get<LineItem>(`${this.baseURL}/${id}`);
  }

  updateLineItem(id: number, quantity: number): Observable<LineItem> {
    return this.http.put<LineItem>(`${this.baseURL}/${id}`, quantity);
  }

  create(lineItemPost: LineItemPost): Observable<LineItem> {
    let quantity = lineItemPost.quantity
    let order_id = lineItemPost.order_id
    let product_id = lineItemPost.product_id
    
    return this.http.post<LineItem>(`${this.baseURL}/${order_id}/${product_id}/${quantity}`, lineItemPost);
  }

  delete(id: number): Observable<LineItem> {
    return this.http.delete<LineItem>(`${this.baseURL}/${id}`);
  }
}
