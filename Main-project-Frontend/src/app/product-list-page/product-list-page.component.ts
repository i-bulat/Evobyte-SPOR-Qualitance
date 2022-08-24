import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subject, takeUntil } from 'rxjs';
import { ProductCategoryService } from '../services/product-category.service';
import { ProductPageService } from '../services/product-page.service';
import { ProductSubCategoryService } from '../services/product-sub-category.service';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';

@Component({
  selector: 'app-product-list-page',
  templateUrl: './product-list-page.component.html',
  styleUrls: ['./product-list-page.component.scss'],
})
export class ProductListPageComponent implements OnInit {
  selectedSubCategoryId: number | undefined;
  selectedCategoryName: string | undefined;
  selectedSubcategoryName: string | undefined;
  currentPage = 1;
  totalPages = 10;
  totalProducts = 0;
  arrProducts: any[] = [];
  arrSubCategory: any[] = [];
  arrPageNumbers: number[] = [];
  isInStock: string = 'No';
  isLoading: boolean = false;



  stopSubscription = new Subject();

  constructor(
    public subCategoryService: ProductSubCategoryService,
    public productPageService: ProductPageService,
    public categoryService: ProductCategoryService,
    private route: ActivatedRoute,
    private _router: Router,

  ) { }

  private setPageNumbers() {
    this.arrPageNumbers = Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  ngOnInit(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.isLoading = false;
    }, 2200);
    this._router.events
      .pipe(
        takeUntil(this.stopSubscription),
        filter((event) => event instanceof NavigationEnd)
      )
      .subscribe((event: any) => {
        this.selectedCategoryName = this.route.snapshot.params['categoryName'];
        this.getSubCategoryNames();
      });

    this.selectedCategoryName = this.route.snapshot.params['categoryName']; //caz in care sunt deja pe o pagina cu categoria selectata
    this.getSubCategoryNames();

  }


  getProductsAndPage(selectedSubCategoryId: number | undefined, page: number) {
    this.productPageService
      .getProductsAndPage(selectedSubCategoryId, page)
      .subscribe((data: any) => {
        this.arrProducts = data.productDtoList;
        this.totalPages = data.totalPages;
        this.totalProducts = data.totalProducts;
        this.isInStock = data.inStock;
        this.setPageNumbers();
      });
  }

  getSubCategoryNames() {
    if (!this.selectedCategoryName) {
      return;
    }
    this.arrSubCategory = [];
    this.subCategoryService.getAll().subscribe((data: any) => {
      for (let i = 0; i < data.length; i++) {
        if (data[i].productCategory.name === this.selectedCategoryName) {
          this.arrSubCategory.push(data[i]);
        }
      }
      this.selectedSubCategoryId = this.arrSubCategory[0]?.id;
      this.selectedSubcategoryName = this.arrSubCategory[0]?.name;
      this.getProductsAndPage(this.selectedSubCategoryId, this.currentPage);
    });
  }

  onSelectSubCategory(subCategoryId: number) {
    this.selectedSubCategoryId = subCategoryId;
    this.selectedSubcategoryName = this.arrSubCategory.find((subCategory) => subCategory.id === subCategoryId)?.name;
    this.getProductsAndPage(subCategoryId, this.currentPage);
  }

  onSelectPage(page: number) {
    this.currentPage = page;
    this.getProductsAndPage(this.selectedSubCategoryId, page);
  }



  ngOnDestroy() {
    this.stopSubscription.next(true);
    this.stopSubscription.complete();
  }
}
