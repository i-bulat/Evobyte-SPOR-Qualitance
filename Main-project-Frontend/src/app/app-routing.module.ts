import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page/home-page.component';
import { AddressDetailsComponent } from './user-profile/address-details/address-details/address-details.component';
import { CardDetailsComponent } from './user-profile/card-details/card-details/card-details.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

import { CreateComponent } from './admin/product-crud/create/create.component';
import { ProductListComponent } from './admin/product-crud/product-list/product-list.component';

import { ProducCategoryListComponent } from './admin/products/produc-category-list/produc-category-list.component';
import { UserLoginComponent } from './user-auth/user-login/user-login.component';
import { UserRegisterComponent } from './user-auth/user-register/user-register.component';
import { UpdateComponent } from './admin/product-crud/update/update.component';

import { DataGenerationComponent } from './admin/data-generation/data-generation.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { OrdersComponent } from './admin/orders/orders.component';
import { CreateSubCategoryComponent } from './admin/SubCategories/create-sub-category/create-sub-category.component';
import { UserListComponent } from './admin/user/user-list/user-list.component';
import { AddUserComponent } from './admin/user/add-user/add-user.component';
import { UpdateUserComponent } from './admin/user/update-user/update-user.component';
import { SearchedProductsComponent } from './components/searched-products/searched-products.component';
import { LineItemComponent } from './admin/lineItem/line-item/line-item.component';
import { ProductCardComponent } from './product-list-page/product-card/product-card.component';
import { ProductListPageComponent } from './product-list-page/product-list-page.component';
import { EditSubCategoriesComponent } from './admin/SubCategories/edit-sub-categories/edit-sub-categories.component';
import { ProductPageComponent } from './product-list-page/product-page/product-page.component';
import { CartComponent } from './cart/cart.component';
import { StarRatingReviewComponent } from './star-rating-review/star-rating-review.component';
import { UserActivationComponent } from './user-auth/user-activation/user-activation.component';
import { CartCheckoutComponent } from './cart/cart-checkout/cart-checkout.component';
import { CardPaymentComponent } from './cart/card-payment/card-payment.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'admin-home',
    component: AdminHomeComponent,
    children: [
      { path: 'data-generation', component: DataGenerationComponent },
      { path: 'product', component: ProductListComponent },
      { path: 'product/create', component: CreateComponent },
      { path: 'product/update/:id', component: UpdateComponent },
      { path: 'order-list', component: OrdersComponent },
      { path: 'product-category-list', component: ProducCategoryListComponent },
      { path: 'create/subcategory', component: EditSubCategoriesComponent },
      { path: 'user-list', component: UserListComponent },
      { path: 'add-user', component: AddUserComponent },
      { path: 'update-user/:id', component: UpdateUserComponent },
      { path: 'line-item', component: LineItemComponent },
    ],
  },

  {
    path: 'search/:id',
    component: SearchedProductsComponent,
  },

  {
    path: '',
    component: HomePageComponent,
  },

  {
    path: 'user-profile',
    component: UserProfileComponent,
  },

  {
    path: 'address-details',
    component: AddressDetailsComponent,
  },
  {
    path: 'card-details',
    component: CardDetailsComponent,
  },

  { path: 'login', component: UserLoginComponent },
  { path: 'register', component: UserRegisterComponent },
  { path: 'auth/register/registrationConfirm', component: UserActivationComponent },

  { path: 'product-page', component: ProductPageComponent },

  {
    path: 'categories/:categoryName',
    component: ProductListPageComponent,
    children: [
      { path: ':subcategory', component: ProductCardComponent },
      { path: 'subcategory/:page', component: ProductCardComponent },
    ],
  },

  { path: 'products/:productId', component: ProductPageComponent },
  { path: 'shopping-cart', component: CartComponent },
  { path: 'product-list', component: ProductListComponent },
  { path: 'star-rating/:productId', component: StarRatingReviewComponent },
  { path: 'cart/checkout', component: CartCheckoutComponent },
  { path: 'card-payment', component: CardPaymentComponent }



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
