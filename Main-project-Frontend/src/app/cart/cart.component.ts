import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { ProductsService } from '../services/products.service';
import { LineitemService } from '../services/lineitem.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  products: Product[] = [];
  public grandTotal: number = 0;
  basketProducts: any[] = [];

  confirmingDelete?: number;


  constructor(

    private cartService: CartService,
    private lineItem: LineitemService

  ) { }

  ngOnInit(): void {
    this.cartService.getProducts()
      .subscribe(res => {
        this.products = res;
      })

    this.basketProducts = this.cartService.basketItems;

    for (let i = 0; i < this.basketProducts.length; i++) {
      this.grandTotal += +this.basketProducts[i].productPrice;
    }

  }
  removeItem(item: Product) {
    this.cartService.removeCartItem(item);
  }
  emptycart() {
    this.cartService.removeAllCart();
  }

  deleteProduct(id?: number) {
    if (this.confirmingDelete === id && id !== undefined) {
      //console.log("id = ", id)
      this.cartService.deleteProduct(id);

      this.getItems();
      this.confirmingDelete = undefined;
    } else {
      this.confirmingDelete = id;
    }
    console.log("id = ", id)
  }

  private getItems() {
    this.products = this.cartService.getItems();
  }

  deleteProduct2(id?: number) {


    if (this.confirmingDelete === id && id !== undefined) {
      console.log("id =", id)
      this.cartService.deleteProduct2(id).subscribe(() => {
        this.cartService.getProducts().subscribe((data: Product[]) => {

          this.products = data;
        });;
        this.confirmingDelete = undefined;
      });

    } else {
      this.confirmingDelete = id;
    }

    console.log(id);
  }


  increment(product: Product) {

    product.quantity += 1;
    this.grandTotal += product.price;

  }


  decrement(product: Product) {

    if (product.quantity > 1) {
      product.quantity -= 1;
      this.grandTotal -= product.price;
    }

  }

  deleteFromBasket(id: number) {
    this.lineItem.delete(id);
    this.cartService.deletefromBasket(id);

  }

}
