import { Component, Input, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {
  @Input() item = {} as any;
  quantity = 1;
  
  constructor( 
    private cartService: CartService,

   ) { }

  ngOnInit(): void { }
 
  addToCart(id: number, quantity: number) {
    this.cartService.addToBasket(id, quantity).subscribe(res => {
      this.cartService.setProduct(res);
    });
  }


}
