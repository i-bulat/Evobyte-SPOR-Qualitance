import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductSubCategory } from 'src/app/models/productSubCategory.model';
import { ProductsService } from 'src/app/services/products.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss'],
})
export class CreateComponent implements OnInit {
  productForm = this.fb.group({
    name: [''],
    description: [''],
    subCategory: [''],
    imageURL: [''],
    price: [''],
    quantity: [''],
  });

  subCategory: ProductSubCategory[] = [];

  constructor(
    public fb: FormBuilder,
    private router: Router,
    private location: Location,
    public productsService: ProductsService
  ) {}

  ngOnInit() {
    this.productsService.getAllSubCategory().subscribe((res) => {
      this.subCategory = res;
    }
    );
  }
  
  submitForm() {
    this.productsService.create(this.productForm.value).subscribe((res) => {
      alert('Product created!');
      this.router.navigateByUrl('admin-home/product');
    });
  }

  back(event: any): void {
    event.preventDefault()
    this.location.back()
  }
}
