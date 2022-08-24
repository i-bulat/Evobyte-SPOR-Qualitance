import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ProductCategory } from 'src/app/models/productCategory.model';
import { ProductSubCategory } from 'src/app/models/productSubCategory.model';
import { ProductCategoryService } from 'src/app/services/product-category.service';

@Component({
  selector: 'app-create-sub-category',
  templateUrl: './create-sub-category.component.html',
  styleUrls: ['./create-sub-category.component.scss']
})
export class CreateSubCategoryComponent implements OnInit {

  constructor(public productCategoryService: ProductCategoryService) { }

  @Output() addedSubCategory = new EventEmitter<ProductSubCategory>();

  Categories: ProductCategory[] = [
  ]

  getCategories(): void {
    this.productCategoryService.getAllCategories().subscribe((data: ProductCategory[]) => {
      console.log(data);
      this.Categories = data;
    });
  }

  ngOnInit(): void {
    this.getCategories();
  }

  addNewSubCategory(f: NgForm): void {
    if (f.value.name == '') {
      alert("Can't create a subCategory without a name");
      return;
    }
    if (f.value.category == '') {
      alert("Can't create a subCategory without a parent Category");
      return;
    }
    if (f.value.description == '') {
      alert("Can't create a subCategory without a description");
      return;
    }
    var new_subCategory: ProductSubCategory = { name: f.value.name, productCategory: f.value.category, description: f.value.description };
    this.addedSubCategory.emit(new_subCategory);
  }


}
