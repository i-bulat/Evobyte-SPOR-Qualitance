import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductsService } from 'src/app/services/products.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isLoading: boolean = false;

  constructor(public productsService: ProductsService,
    public cartService: CartService
  ) { }

  ngOnInit() {
    this.isLoading = true;

    this.productsService.getAll().subscribe((data: Product[]) => {
      console.log(data);
      this.products = data;
      setTimeout(() => {
        this.isLoading = false;
      }, 2200);

    });



  }

  delete(id: number | undefined) {
    this.productsService.delete(id).subscribe((data: Product) => {
      console.log(data);
      this.products = this.products.filter((product) => product.id !== id);
    });
  }

  submitData(value: any) {
    let body: Product = {
      name: value.name,
      description: value.description,
      subCategory: value.subCategory,
      imageUrl: value.imageUrl,
      price: value.price,
      quantity: value.quantity,
    }
    this.productsService.create(body)
      .subscribe(response => {
        console.log(response)
      })
  }

  updateData(id: number, value: any) {
    let body = {
      name: value.name,
      description: value.description,
      subCategory: value.subCategory,
      quantity: value.quantity,
      imageUrl: value.imageUrl,
      price: value.price,

    }
    this.productsService.update(id, body)
      .subscribe(response => {
        console.log(response)
      })
  }

  deleteData(id: number) {
    this.productsService.delete(id)
      .subscribe(response => {
        console.log(response);
      })
  }
}
