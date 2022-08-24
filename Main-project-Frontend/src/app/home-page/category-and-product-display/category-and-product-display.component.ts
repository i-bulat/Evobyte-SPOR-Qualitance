import { AfterViewInit, Component, OnInit } from '@angular/core';
//import bulmaCarousel from 'bulma-carousel/dist/js/bulma-carousel.min.js';
import bulmaCarousel from 'bulma-carousel';
//import { privateDecrypt } from 'crypto';
import { Observable } from 'rxjs/internal/Observable';
import { Product } from 'src/app/models/product.model';
import { ProductCategory } from 'src/app/models/productCategory.model';
import { ProductCategoryService } from 'src/app/services/product-category.service';
import { ProductsService } from 'src/app/services/products.service';

//const bulmaCarousel = require('bulma-carousel');

@Component({
  selector: 'app-category-and-product-display',
  templateUrl: './category-and-product-display.component.html',
  styleUrls: ['./category-and-product-display.component.scss']
})
export class CategoryAndProductDisplayComponent implements AfterViewInit {

  topCategories: ProductCategory[] = [];

  topProducts: any = [];


  tempProducts: Product[] = [];
  constructor(
    private productService: ProductsService,
    private categoryService: ProductCategoryService
  ) { };

  ngAfterViewInit(): void {

    bulmaCarousel.attach('#carousel-demo', {
      slidesToScroll: 1,
      slidesToShow: 5,
      pagination: false
    });

    this.categoryService.getTopCategories().subscribe((data: ProductCategory[]) => {
      // console.log(data);
      this.topCategories = data;
      console.log(this.topCategories);
      console.log(this.topCategories.length);
      for (let i = 0; i < this.topCategories.length; i++) {
        this.productService.getTopProducts(this.topCategories[i].id!).subscribe((res: Product[]) => {
          this.tempProducts = res
          console.log(res)
          for (let j = 0; j < this.tempProducts.length; j++) {
            this.topProducts.push(
              {
                name: this.tempProducts[j].name,
                price: this.tempProducts[j].price,
                description: this.tempProducts[j].description,
                category: this.topCategories[i].id,
                index: j
              }
            )
          }
        })
      }
    });

  }

}
