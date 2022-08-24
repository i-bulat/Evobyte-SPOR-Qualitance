import { Component, OnInit } from '@angular/core';
import { LineItem, LineItemPost } from 'src/app/models/line-item.model';
import { LineitemService } from 'src/app/services/lineitem.service';

@Component({
  selector: 'app-line-item',
  templateUrl: './line-item.component.html',
  styleUrls: ['./line-item.component.scss']
})
export class LineItemComponent implements OnInit {
  lineItem: LineItemPost = {}
  lineItems: LineItem[] = []

  constructor(private lineItemService: LineitemService) {}

  ngOnInit(): void {}

  addLineItem() {
    this.lineItemService.create(this.lineItem!).subscribe(
      (data) => {}
    )
  }

  updateLineItem() {
    this.lineItemService.updateLineItem(this.lineItem.id!, this.lineItem.quantity!).subscribe(
      (data) => {}
    )
  }

}
