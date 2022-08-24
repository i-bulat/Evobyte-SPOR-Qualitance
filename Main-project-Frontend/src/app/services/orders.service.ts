import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { APIbaseURL } from '../settings';
import { Order, OrderPost } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  orders: Order[] = []

  constructor(private http: HttpClient) { }

  // private baseURL = APIbaseURL;
  private baseURL = `${APIbaseURL}/orders`;
  getAll(): Observable<Order[]> {
    console.log(`${this.baseURL}`)
    return this.http.get<Order[]>(`${this.baseURL}`);
  }

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.baseURL}/${id}`);
  }

  updateOrderStatus(id: number, status: String): Observable<Order> {
    return this.http.put<Order>(`${this.baseURL}/${id}`, status);
  }

  create(data: OrderPost): Observable<Order> {
    console.log(`${this.baseURL}`, data)
    return this.http.post<Order>(`${this.baseURL}`, data);
  }

  delete(id: number): Observable<Order> {
    return this.http.delete<Order>(`${this.baseURL}/${id}`);
  }

  validateCreditCard(data: any): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/validateCreditCard`, { params: data });
  }

  updateOrderStatus2(id: number, status: String): Observable<any> {
    return this.http.put<any>(`${this.baseURL}/${id}/?status=${status}`, {});
  }

}
//////https://www.javaguides.net/2021/08/angular-crud-example.html