import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { OrdersService } from 'src/app/services/orders.service';
import { CartComponent } from '../cart.component';

@Component({
  selector: 'app-card-payment',
  templateUrl: './card-payment.component.html',
  styleUrls: ['./card-payment.component.scss'],
})
export class CardPaymentComponent implements OnInit {
  currentOrderId: number = 0;

  constructor(
    public formBuilder: FormBuilder,
    private orderService: OrdersService,
    private cartService: CartService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.currentOrderId = this.cartService.basketItems[0].orderId;
    console.log('ora 2250 ' + this.currentOrderId);
  }

  cardPaymentForm = this.formBuilder.group({
    cardName: [''],
    cardNumber: [''],
    cardExpiry: [''],
    cardCvc: [''],
  });

  payWithCard() {
    console.log(this.cardPaymentForm.value);
    this.orderService
      .validateCreditCard({
        cardNo: this.cardPaymentForm.value.cardNumber,
        expirationDate: this.cardPaymentForm.value.cardExpiry +`/`,
        CVV: this.cardPaymentForm.value.cardCvc,
      })
      .subscribe({
        next: (res) => {
          this.orderService.updateOrderStatus2(this.cartService.basketItems[0].orderId, 'COMPLETED').subscribe(
          );
          alert('Congratulations! Your order has been placed!');
          this.router.navigate(['/']);
          this.cartService.removeAllCart();
        },
        error: (err) => {
          alert('Invalid card details, please try again!');
        },
      });
    
  }
}
