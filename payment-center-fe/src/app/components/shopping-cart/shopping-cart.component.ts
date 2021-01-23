import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Subscription } from 'rxjs';
import { CartService } from 'src/app/services/cart.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { CartItem } from 'src/app/shared/models/cart-item';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit, OnDestroy {

  private subscription: Subscription;

  displayedColumns: string[] = ['book', 'quantity', 'price', 'actions'];
  dataSource: MatTableDataSource<any>;
  totalPrice: number = 0;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private formBuilder: FormBuilder,
    private snackbarService: SnackBarService, private cartService: CartService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.refreshDataSource();
    this.subscription = this.cartService.items.subscribe(items => {
      this.dataSource.data = items;
      this.totalPrice = 0;
      this.dataSource.data.forEach(element => {
        this.totalPrice += element.price;
      });
    });

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  refreshDataSource() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  removeFromCart(orderItem: CartItem) {
    this.cartService.removeItem(orderItem.id);
  }


  emptyCart() {
    this.cartService.empty();
  }


}
