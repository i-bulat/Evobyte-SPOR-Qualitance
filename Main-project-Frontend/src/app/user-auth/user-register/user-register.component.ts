import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserInfo } from 'src/app/models/userInfo.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private authService: AuthenticationService, private userService: UserService) {
    this.needToConfirmRegistration = false;
   }

  needToConfirmRegistration = false;
  userToRegister!: User;
  errorMessage = "";

  ngOnInit(): void {
    this.needToConfirmRegistration = false;
  }
  registerForm = this.formBuilder.group({
    username: [''],
    email: [''],
    password: [''],
    first_name: [''],
    last_name: [''],
    confirmedPassword: ['']
  });

  isUserEnabled() {
    this.authService.login(this.userToRegister.username!, this.userToRegister.password!).subscribe({
      next: (data) => {
        return true
      },
      error: (data) => {
        console.log('Error Encountered!')
        alert('Cannot login!')
      }
    });


  }

  register() {
    if(this.registerForm.get('username')!.value === "" || this.registerForm.get('email')!.value === "" ||
       this.registerForm.get('password')!.value === "" || this.registerForm.get('first_name')!.value === "" || 
       this.registerForm.get('last_name')!.value === "") {
       this.errorMessage = "Please fill in all the required data!";
       return;
    }

    if(this.registerForm.get('password')!.value !== this.registerForm.get('confirmedPassword')!.value) {
      this.errorMessage = "Please confirm your password!";
      return;
    }

    var formData: any = new FormData();
    formData.append("username", this.registerForm.get('username')!.value);
    formData.append("email", this.registerForm.get('email')!.value);
    formData.append("password", this.registerForm.get('password')!.value);
    formData.append("first_name", this.registerForm.get('first_name')!.value);
    formData.append("last_name", this.registerForm.get('last_name')!.value);
    console.log(formData);

    this.authService.register(formData).subscribe({
      next: (data: UserInfo) => {
        console.log('User registered')
        this.needToConfirmRegistration = true;

        this.userService.getUserByUsername(this.registerForm.get('username')!.value).subscribe(
          (data) => {
            let user = data
            this.userToRegister = user
          }
        )

        this.errorMessage = "";
      },
      error: (data: UserInfo) => {
        console.log('Error Encountered!')
      }
    });
  }
}
