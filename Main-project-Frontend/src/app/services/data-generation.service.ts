import { Injectable } from '@angular/core';
import { ProductsService } from 'src/app/services/products.service';
import { UserService } from 'src/app/services/user.service';
import { OrdersService } from 'src/app/services/orders.service';
import { ProductCategoryService } from 'src/app/services/product-category.service';
import { OrderPost } from '../models/order.model';
import { ProductSubCategoryService } from './product-sub-category.service';
import { ProductSubCategory } from '../models/productSubCategory.model';
import * as moment from 'moment';


@Injectable({
  providedIn: 'root'
})
export class DataGenerationService {

  constructor(
    public productsService: ProductsService,
    public userService: UserService,
    public orderService: OrdersService,
    public categoryService: ProductCategoryService,
    public subCategoryService: ProductSubCategoryService,
  ) { }

  generateProducts(count: number, arr: any[]) {
    let products = [];
    for (let i = 0; i < count; i++) {
      products.push({
        name: 'Product ' + (i + 1),
        price: Math.floor(Math.random() * 100),
        description: 'Product ' + (i + 1) + ' description',
        imageUrl: 'https://via.placeholder.com/150',
        subCategory: arr[Math.floor(Math.random() * arr.length)],
        quantity: Math.floor(Math.random() * 100),
      });
      this.productsService.create(products[i]).subscribe(() => {
        console.log('Product created!', i);
      });
    }
    return products;
  }

  generateUsers(count: number) {
    let users = [];
    for (let i = 0; i < count; i++) {
      users.push({
        firstName: 'FirstName ' + (i + 1),
        lastName: 'LastName ' + (i + 1),
        email: 'email' + (i + 1) + '@mail.ro',
        password: 'password' + (i + 1),
      });
      this.userService.create(users[i]).subscribe(() => {
        console.log('User created!', i);
      });
    }
    return users;
  }

  generateOrders(count: number) {
    console.log('Order created!');
    let orders: OrderPost[] = [];
    let nrUsers = 0
    this.userService.getAll().subscribe(
      (data) => {
        nrUsers = data.length
      }
    )
    for (let i = 0; i < count; i++) {
      let userId = Math.floor(Math.random() * nrUsers)

      this.userService.getUserByID(userId).subscribe(
        (data) => {
          orders.push({
            dateCreated: moment().format('DD-MM-YYYY'),
            status: 'IN_BASKET',
            user: data
          });
        }
      )
      this.orderService.create(orders[i]).subscribe(() => {
        console.log('Order created!', i);
      });

    }
    return orders;
  }

  generateCategories(count: number) {
    let categories = [];
    for (let i = 0; i < count; i++) {
      categories.push({
        name: 'Category ' + (i + 1),
        description: 'Category ' + (i + 1) + ' description',
      });
      this.categoryService.addCategory(categories[i]).subscribe(() => {
        console.log('Category created!', i);
      });
    }
    return categories;
  }

  generateSubCategories(count: number, arr: any[]) {
    let subCategories: ProductSubCategory[] = [];
    for (let i = 0; i < count; i++) {
      subCategories.push({
        name: 'SubCategory ' + (i + 1),
        description: 'SubCategory ' + (i + 1) + ' description',
        productCategory: arr[Math.floor(Math.random() * arr.length)],
      });
      this.subCategoryService.create(subCategories[i]).subscribe(() => {
        console.log('SubCategory created!', i);
      });
    }
  }

}
