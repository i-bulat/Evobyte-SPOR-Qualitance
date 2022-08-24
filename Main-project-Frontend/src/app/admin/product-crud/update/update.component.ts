import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductsService } from 'src/app/services/products.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.scss'],
})
export class UpdateComponent implements OnInit {
  id: number | undefined;
  productsCategory: Product[] = [];

  productForm = this.fb.group({
    name: [''],
    description: [''],
    subCategory: [''],
    imageURL: [''],
    price: [''],
    quantity: [''],
  });

  constructor(
    public fb: FormBuilder,
    private router: Router,
    public productsService: ProductsService,
    private route: ActivatedRoute,
    private location: Location,
  ) { }

  ngOnInit() {
    this.id = +this.route.snapshot.params['id'];
    this.productsService.getAllSubCategory().subscribe((res) => {
      this.productsCategory = res;
    });
    this.productsService.getById(this.id).subscribe((res) => {
      console.log(res);
      this.productForm.patchValue({
        name: res.name,
        description: res.description,
        subCategory: "",
        imageUrl: res.imageUrl,
        price: res.price,
        quantity: res.quantity,
      });

    });
  }


  submitForm() {
    this.productsService.update(this.id as any, this.productForm.value).subscribe((res) => {
      alert('Product updated!');
      this.router.navigateByUrl('/admin-home/product');
    });
  }
  back(event: any): void {
    event.preventDefault()
    this.location.back()
  }
}
