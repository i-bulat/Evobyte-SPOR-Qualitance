import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Event, Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CartService } from 'src/app/services/cart.service';
import { OrdersService } from 'src/app/services/orders.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-cart-checkout',
  templateUrl: './cart-checkout.component.html',
  styleUrls: ['./cart-checkout.component.scss']
})
export class CartCheckoutComponent implements OnInit {
  totalPrice: number = 0;
  shippingAddress: string | undefined;
  currentUserId: number | undefined;
  currentUser: User | undefined;



  constructor(
    private cartService: CartService,
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private orderService: OrdersService,
    private authenticationService: AuthenticationService,
  ) { }

  shippingForm = this.formBuilder.group({
    fullName: [''],
    email: [''],
    phoneNumber: [''],
    country: [''],
    city: [''],
    address: [''],
    observations: ['']
  });

  ngOnInit(): void {

    this.currentUserId = this.authenticationService.loggedInUser.id;
    console.log(' id userul curent ' + this.currentUserId);
    this.totalPrice = this.cartService.getTotalPrice();
    //  this.userService.getUserAddress().subscribe(res => {
    //   this.shippingAddress = res.address;
    // });

    this.userService.getUserById2(this.currentUserId).subscribe(res => {
      this.currentUser = res;
      this.shippingForm.patchValue({
        fullName: this.currentUser.firstName + ' ' + this.currentUser.lastName,
        email: this.currentUser.email,
        address: this.shippingAddress
      });
    });

  }


  onSubmit() {

    this.orderService.updateOrderStatus2(this.cartService.basketItems[0].orderId, 'PLACED').subscribe({
      next: (res) => {
        this.router.navigate(['/card-payment'])
      },
    });

  }

}
