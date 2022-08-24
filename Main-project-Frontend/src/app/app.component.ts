import { Component } from '@angular/core';
import { ProductsService } from './services/products.service';
import { Router } from '@angular/router';
import { User } from './models/user.model';
import { AuthenticationService } from './services/authentication.service';
import { ActivatedRoute } from '@angular/router';
import { ProductCategoryService } from './services/product-category.service';
import { ProductSubCategoryService } from './services/product-sub-category.service';
import { CartService } from './services/cart.service';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'main-project-frontend';
  public totalItem: number = 0;
  isLoading: boolean = false;

  constructor(
    public productsService: ProductsService,
    private router: Router,
    public categoryService: ProductCategoryService,
    private cartService: CartService,
    public authenticationService: AuthenticationService,
    public userService: UserService
  ) { }

  ngOnInit() {

    this.getCategoryNames();
    this.cartService.getProducts().subscribe(res => {
      this.totalItem = res.length;
    });
    this.userService.refreshList()
  }

  categories: any[] = [];

  getCategoryNames(): void {
    this.categoryService.getAllCategories().subscribe((data: any) => {
      this.categories = data;
    }
    );
  }

  isLoggedIn: boolean = false;
  user!: User;

  logIn() {
    this.isLoggedIn = true;
  }

  logOut() {
    this.isLoggedIn = false;
    this.authenticationService.logout()
  }

  //============ Search Bar ===========

  currentSearch: string = ""
  suggestions: string[] = [];
  isFocused: boolean = false;
  canLoseFocus: boolean = true;

  changedSearchInput(): void {
    this.isFocused = true;
    this.productsService.getSearchSuggestions(this.currentSearch).subscribe((data: string[]) => {
      this.suggestions = data;
    });
  }

  maybeLoseFocus(): void {
    if (this.canLoseFocus == true)
      this.isFocused = false;
  }

  searchProducts(): void {
    this.isFocused = false;
    this.router.navigateByUrl(`/search/${this.currentSearch}`);
  }
}
