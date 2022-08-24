import { Component, OnInit } from '@angular/core';
import { ProductCategoryService } from 'src/app/services/product-category.service';
import { ProductCategory } from 'src/app/models/productCategory.model';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  public categories: ProductCategory[] = [];

  public newCategory: ProductCategory | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private ProductCategoryService: ProductCategoryService,
    private router: Router,
    private userService: UserService,
    private authService: AuthenticationService
  ) { }

  // inputForm = this.formBuilder.group({
  //   name: ['', [Validators.required, Validators.minLength(1)]],
  //   description: ['', [Validators.required, Validators.minLength(1)]],
  // });ng

  ngOnInit(): void {
    // this.ProductCategoryService.getAllCategories().subscribe((data) => this.categories = data);
    // console.log(this.categories);
    

    let url = String(this.router.url)
    console.log(url)
    url = url.replace('/?token=', '')

    if(url == '' || url == '/')
      return

    let token = url

    localStorage.setItem('token', token)
    this.router.navigate(['/']);

    let decodedToken = atob(url.split('.')[1])

    let i = 0, cnt = 0
    let username = ""

    while(cnt < 4) {
      if(cnt == 3 && decodedToken[i] != '"')
        username += decodedToken[i]
      if(decodedToken[i] == '"')
        cnt += 1
      i += 1
    }

    //let user = this.userService.getUserByUsername(username)

    this.userService.getUserByUsername(username).subscribe(
      (data) => {
        let user = data
        this.authService.loggedInUser = user
        localStorage.setItem('currentUser', JSON.stringify(user))
      }
    )
  }

}
