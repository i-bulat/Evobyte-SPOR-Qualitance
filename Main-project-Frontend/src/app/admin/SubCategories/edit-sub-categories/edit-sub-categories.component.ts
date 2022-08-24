import { Component, OnInit } from '@angular/core';
import { ProductCategory } from 'src/app/models/productCategory.model';
import { ProductSubCategory } from 'src/app/models/productSubCategory.model';
import { ProductSubCategoryService } from 'src/app/services/product-sub-category.service';
import { ProductsService } from 'src/app/services/products.service';
import { ProductCategoryService } from 'src/app/services/product-category.service';

@Component({
  selector: 'app-edit-sub-categories',
  templateUrl: './edit-sub-categories.component.html',
  styleUrls: ['./edit-sub-categories.component.scss']
})
export class EditSubCategoriesComponent implements OnInit {

  subCategories: ProductSubCategory[] = [
  ];

  hasChanged: boolean[] = [];

  Categories: ProductCategory[] = [
  ];


  constructor(public productsService: ProductsService, public productSubCategoryService: ProductSubCategoryService, public productCategoryService: ProductCategoryService) { }

  getSubCategories(): void {
    this.productSubCategoryService.getAll().subscribe((data: ProductSubCategory[]) => {
      console.log(data);
      this.subCategories = data;
    });
    for (var i = 0; i < this.subCategories.length; ++i) // *
      this.hasChanged[i] = false;
  }

  getCategories(): void {
    this.productCategoryService.getAllCategories().subscribe((data: ProductCategory[]) => {
      console.log(data);
      this.Categories = data;
    });
  }

  ngOnInit(): void {
    this.getSubCategories();
    this.getCategories();
  }

  changeSubCategory(id?: number, i?: number): void {
    if (id == undefined || i == undefined) {
      alert("something went wrong while updating a subCategory");
      return;
    }
    if (this.hasChanged[i] == false)
      return;
    this.productSubCategoryService.update(id, this.subCategories[i]).subscribe((data: ProductCategory) => {
      this.getSubCategories();
    });
  }

  deleteSubCategory(id?: number): void {
    if (id == undefined) {
      alert("something went wrong while deleting a subCategory")
      return;
    }
    if (confirm("Are you sure you want to delete this subCategory?")) {
      this.productSubCategoryService.delete(id).subscribe((data: ProductSubCategory) => {
        this.getSubCategories();
      });
      this.getSubCategories();
    }
  }

  addSubCategory(new_subCategory: ProductSubCategory): void {
    this.productSubCategoryService.create(new_subCategory).subscribe((data: ProductSubCategory) => {
      console.log('SubCategory Created');
      this.getSubCategories();
    });
  }

}
