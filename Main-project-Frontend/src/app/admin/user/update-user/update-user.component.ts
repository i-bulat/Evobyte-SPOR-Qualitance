import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {
  constructor(
    public formupdate: FormBuilder,
    private router: Router,
    public userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) { }




  formUpdate = this.formupdate.group({

    firstName: [''],
    lastName: [''],
    email: [''],
    password: [''],


  });



  ngOnInit(): void {

    //const id = this.route.snapshot.paramMap.get('id');
    //console.log(id);



  }


  back(event: any): void {
    event.preventDefault()
    this.location.back()
  }

  updateForm() {



    // this.userService.update(this.id, this.formUpdate.value).subscribe((data: User) => {

    //   console.log('User updated!');
    //   this.router.navigateByUrl('');
    // });

    const id = +this.route.snapshot.paramMap.get('id')!;
    console.log(id);

    this.userService.update(id, this.formUpdate.value).subscribe((data: User) => {
      alert('User updated!');
      this.router.navigateByUrl('admin-home/user-list');
    });

  }
}
