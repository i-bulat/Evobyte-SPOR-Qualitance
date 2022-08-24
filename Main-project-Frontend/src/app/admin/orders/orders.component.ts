import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Order, OrderPost } from 'src/app/models/order.model';
import { UserService } from 'src/app/services/user.service';
import * as moment from 'moment';
import { OrdersService } from 'src/app/services/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {
  orders: Order[] = []

  user_id = 1

  order: Order = {
    dateCreated: moment().format('DD-MM-YYYY'),
    status: "PLACED",
    lineItems: [],
    totalPrice:  0
  };

  id = 0

  constructor(private ordersService: OrdersService, private userService: UserService) {
    this.refreshOrders()
  }

  refreshOrders() {
    this.ordersService.getAll().subscribe(
      (data) => {
        console.log(data, 'data')
        this.orders = data
      }
    )
  }

  ngOnInit(): void {
    this.refreshOrders()
  }

  addOrder() {
    console.log(moment().format('DD-MM-YYYY'))
    this.userService.getUserByID(this.user_id).subscribe(
      (data) => {
        let sendOrder: OrderPost = {
          dateCreated: this.order.dateCreated,
          status: this.order.status,
          user: data
        }
        
        this.ordersService.create(sendOrder).subscribe(
          (data) => {
            this.refreshOrders()
          }
        )
      }
    )
  }

  getOrder(id: number) {
    this.ordersService.getOrder(id).subscribe(
      (data) => {
        this.refreshOrders()
      }
    )
  }

  deleteOrder(index: number) {
    this.ordersService.delete(index).subscribe(
      (data) => {
        this.refreshOrders()
      }
    )
  }

  updateOrder() {
    this.ordersService.updateOrderStatus(this.order.id!, this.order.status).subscribe(
      (data) => {
        this.refreshOrders()
      }
    )
  }

}

