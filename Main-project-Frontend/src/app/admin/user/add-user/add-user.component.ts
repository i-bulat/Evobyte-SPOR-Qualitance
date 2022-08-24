import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  productForm = this.fb.group({

    firstName: [''],
    lastName: [''],
    email: [''],
    password: ['']

  });

  ngOnInit(): void {


  }


  constructor(
    public fb: FormBuilder,
    private router: Router,
    public userService: UserService,
    private location: Location
  ) { }




  submitForm() {
    console.log(this.productForm.value);
    this.userService.create(this.productForm.value).subscribe((data: User) => {

      console.log('User created!');
      this.router.navigateByUrl('admin-home/user-list');
    });
  }

  back(event: any): void {
    event.preventDefault()
    this.location.back()
  }
}
