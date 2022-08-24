import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  userList: User[] = [];
  confirmingDelete?: number;
  @Input() todoItem!: User;
  @Output() DeleteUser = new EventEmitter<number>();

  constructor(
    public userService: UserService
  ) { }

  ngOnInit() {

    this.userService.getAll().subscribe((data: User[]) => {
      console.log(data);
      this.userList = data;
    });

  }

  deleteUser(id?: number) {


    if (this.confirmingDelete === id && id !== undefined) {
      console.log(id)
      this.userService.delete(id).subscribe(() => {
        this.userService.getAll().subscribe((data: User[]) => {

          this.userList = data;
        });;
        this.confirmingDelete = undefined;
      });

    } else {
      this.confirmingDelete = id;
    }
  }
}
