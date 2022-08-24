import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgCreditCardModule } from "angular-credit-card";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserLoginComponent } from './user-auth/user-login/user-login.component';
import { UserRegisterComponent } from './user-auth/user-register/user-register.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProductListComponent } from './admin/product-crud/product-list/product-list.component';
import { CreateComponent } from './admin/product-crud/create/create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { CardDetailsComponent } from './user-profile/card-details/card-details/card-details.component';
import { AddressDetailsComponent } from './user-profile/address-details/address-details/address-details.component';
import { HomePageComponent } from './home-page/home-page/home-page.component';

import { UpdateComponent } from './admin/product-crud/update/update.component';

import { ProducCategoryListComponent } from './admin/products/produc-category-list/produc-category-list.component';
import { DataGenerationComponent } from './admin/data-generation/data-generation.component';
import { DatePipe } from '@angular/common';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { OrdersComponent } from './admin/orders/orders.component';
import { CreateSubCategoryComponent } from './admin/SubCategories/create-sub-category/create-sub-category.component';
import { EditSubCategoriesComponent } from './admin/SubCategories/edit-sub-categories/edit-sub-categories.component';
import { AddUserComponent } from './admin/user/add-user/add-user.component';
import { UpdateUserComponent } from './admin/user/update-user/update-user.component';
import { UserListComponent } from './admin/user/user-list/user-list.component';
import { CategoryAndProductDisplayComponent } from './home-page/category-and-product-display/category-and-product-display.component';
import { SearchedProductsComponent } from './components/searched-products/searched-products.component';
import { ProductListPageComponent } from './product-list-page/product-list-page.component';
import { ProductCardComponent } from './product-list-page/product-card/product-card.component';
import { LineItemComponent } from './admin/lineItem/line-item/line-item.component';
import { ProductPageComponent } from './product-list-page/product-page/product-page.component';
import { CartComponent } from './cart/cart.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StarRatingReviewComponent } from './star-rating-review/star-rating-review.component';
import { TokenInterceptor } from './services/tokenInterceptor';
import { UserActivationComponent } from './user-auth/user-activation/user-activation.component';
import { CartCheckoutComponent } from './cart/cart-checkout/cart-checkout.component';
import { CardPaymentComponent } from './cart/card-payment/card-payment.component';


@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    UserRegisterComponent,
    ProductListComponent,
    CreateComponent,
    UserProfileComponent,
    CardDetailsComponent,
    AddressDetailsComponent,
    HomePageComponent,
    UpdateComponent,
    ProducCategoryListComponent,
    DataGenerationComponent,
    AdminHomeComponent,
    OrdersComponent,
    CreateSubCategoryComponent,
    EditSubCategoriesComponent,
    AddUserComponent,
    UpdateUserComponent,
    UserListComponent,
    CategoryAndProductDisplayComponent,
    SearchedProductsComponent,
    LineItemComponent,
    ProductCardComponent,
    ProductListPageComponent,
    ProductPageComponent,
    CartComponent,
    StarRatingReviewComponent,
    UserActivationComponent,
    CartCheckoutComponent,
    CardPaymentComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgCreditCardModule
  ],
  providers: [
    DatePipe,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
