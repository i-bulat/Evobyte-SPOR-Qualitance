import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.scss']
})
export class CardDetailsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  cardCard: boolean = false;

  newCardPopUp() {
    this.cardCard = true;
  }

  newCardDissapear() {
    this.cardCard = false;
  }
}
