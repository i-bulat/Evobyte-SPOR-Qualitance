import { TokenizeResult } from '@angular/compiler/src/ml_parser/lexer';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { UserInfo } from 'src/app/models/userInfo.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {
  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthenticationService, private userService: UserService) {

  }

  ngOnInit(): void {}

  logInForm = this.formBuilder.group({
    username: [''],
    password: ['']
  });


  login() {
    this.authService.login(this.logInForm.value.username, this.logInForm.value.password).subscribe({
      next: (data) => {
        console.log('User logged in')
        this.userService.getUserByUsername(this.logInForm.value.username).subscribe(
          (data) => {
            let user = data
            this.authService.loggedInUser = user
            localStorage.setItem('currentUser', JSON.stringify(user))
          }
        )
      },
      error: (data) => {
        console.log('Error Encountered!')
        alert('Cannot login!')
      }
    });
  }
}
