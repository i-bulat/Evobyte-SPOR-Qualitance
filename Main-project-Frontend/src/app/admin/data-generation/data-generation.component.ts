import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductSubCategory } from 'src/app/models/productSubCategory.model';
import { DataGenerationService } from 'src/app/services/data-generation.service';
import { ProductCategoryService } from 'src/app/services/product-category.service';
import { ProductSubCategoryService } from 'src/app/services/product-sub-category.service';


@Component({
  selector: 'app-data-generation',
  templateUrl: './data-generation.component.html',
  styleUrls: ['./data-generation.component.scss']
})
export class DataGenerationComponent implements OnInit {
  count: number = 1;
  productSubCategoryId: number | undefined;
  productCategoryId: any[] = [];
  arrSubCategoryId: any[] = [];
  arrCategoryId: any[] = [];

  constructor(
    private dataGenerationService: DataGenerationService,
    private router: Router,
    private subCategoryService: ProductSubCategoryService,
    private categoryService: ProductCategoryService

  ) { }

  ngOnInit(): void {
    this.randomSubCatId();
    this.randomCatId();

  }

  randomSubCatId() {
    this.subCategoryService.getAll().subscribe((res) => {
      for (let i = 0; i < res.length; i++) {
        this.arrSubCategoryId.push(res[i].id);
      }
      console.log('subcategorii aleatoare ' + this.arrSubCategoryId);
    }
    );
  }

  randomCatId() {
    this.categoryService.getAllCategories().subscribe((res) => {
      for (let i = 0; i < res.length; i++) {
        this.arrCategoryId.push(res[i].id);
      }
      console.log('categorii aleatoare ' + this.arrCategoryId);
    }
    );
  }

  generateProducts(count: number) {
    this.dataGenerationService.generateProducts(count, this.arrSubCategoryId);
    this.router.navigateByUrl('admin-home/product');
  }
  generateUsers(count: number) {
    this.dataGenerationService.generateUsers(count);
    this.router.navigateByUrl('/admin-home/user-list');
  }
  generateOrders(count: number) {
    this.dataGenerationService.generateOrders(count);
    this.router.navigateByUrl('admin-home');
  }
  generateCategories(count: number) {
    this.dataGenerationService.generateCategories(count);
    this.router.navigateByUrl('/admin-home/product-category-list');
  }

  generateSubCategories(count: number) {
    this.dataGenerationService.generateSubCategories(count, this.arrCategoryId);
    this.router.navigateByUrl('/admin-home/create/subcategory');
  }

  generateAll(count: number) {

    this.dataGenerationService.generateUsers(count);
    this.dataGenerationService.generateOrders(count);
    this.dataGenerationService.generateCategories(count);
    this.router.navigateByUrl('admin-home');
  }


}