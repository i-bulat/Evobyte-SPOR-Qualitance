import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { HttpClient } from '@angular/common/http';
import { APIbaseURLcart } from '../settings';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { ProductsService } from './products.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  found: boolean = false;
  items: Product[] = [];
  basketItems: any[] = [];
  idCart: number = 0;
  public productList = new BehaviorSubject<any>([]);

  public search = new BehaviorSubject<string>('');
  product?: Product | undefined;
  tempProduct: any;

  constructor(private http: HttpClient, private products: ProductsService) { }

  private baseURL = APIbaseURLcart;

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseURL}/products`);
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseURL}/products/${id}`);
  }

  deletefromBasket(id: number) {

    this.basketItems.forEach(element => {
      if (element.id === id) {
        this.basketItems.splice(this.basketItems.indexOf(element), 1);
      }

    });

  }

  setProduct(product: any) {
    this.products.getById(product.productId).subscribe((data: Product) => {
      this.tempProduct = data;
      this.basketItems.push({
        productId: this.tempProduct.id,
        productName: this.tempProduct.name,
        productPrice: this.tempProduct.price,
        productImage: this.tempProduct.imageUrl,
        productQuantity: product.quantity,
        orderId: product.orderId,
        lineItemId: product.id,
      });
      this.productList.next(this.basketItems);
    });


  }

  addtoCart(product: any) {
    this.items.push(product);
    this.productList.next(this.items);
  }

  addToBasket(productId: number, quantity: number): Observable<Product> {
    return this.http.post<Product>(
      `${this.baseURL}/lineItems/${productId}/${quantity}`,
      {}
    );
  }

  addtoCartById(id: number) {
    this.getProduct(id).subscribe((data: Product) => {
      this.product = data;
      this.addtoCart(this.product);
    });
  }

  quantity() {
    let quantity = 1;
    this.items.map((x: Product) => {
      quantity += x.quantity;
    });
    console.log('qunatity = ', quantity);
    return quantity;
  }

  getTotalPrice(): number {
    let grandTotal = 0;
    for (let i = 0; i < this.basketItems.length; i++) {
      grandTotal += +this.basketItems[i].productPrice;
    }
    return grandTotal;
  }
  removeCartItem(product: Product) {

    this.items = this.items.filter((item) => item.id !== product.id);

    this.productList.next(this.items);
  }
  removeAllCart() {
    this.items = [];
    this.basketItems = [];
    this.productList.next(this.basketItems);
  }

  getProducts() {
    return this.productList.asObservable();
  }

  deleteProduct(id: number) {
    this.items = this.items.filter((product) => product.id !== id);
    this.productList.next(this.items);
  }

  getItems() {
    return this.items;
  }

  deleteProduct2(id?: number): Observable<void> {
    if (!id) {
      return of();
    }
    console.log(id);
    return this.http.delete<void>(`${this.baseURL}/product/delete/${id}`);
  }

}
