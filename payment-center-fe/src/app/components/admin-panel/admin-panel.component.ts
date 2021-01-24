import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MatTabsModule } from '@angular/material/tabs'
import { AuthService } from 'src/app/services/auth.service';
import { PaymentMethodService } from 'src/app/services/payment-method.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  paymentMethods: [];
  clients: [];
  displayedColumnsPaymentMethods: string[] = ['name', 'subscriptionSupported', 'serviceId', 'actions'];
  displayedColumnsClients: string[] = ['name', 'email', 'taxIdentificationNumber', 'companyRegistrationNumber', 'actions'];
  dataSourcePaymentMethods: MatTableDataSource<any>;
  dataSourceClients: MatTableDataSource<any>;

  @ViewChild(MatSort, { static: true }) sortPaymentMethodsTable: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginatorPaymentMethodsTable: MatPaginator;

  @ViewChild(MatSort, { static: true }) sortClientsTable: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginatorClientsTable: MatPaginator;



  constructor(private paymentMethodService: PaymentMethodService, private snackbarService: SnackBarService, private clientService: UserService, private authService: AuthService) { }

  ngOnInit() {
    this.getPaymentMethods();
  }
  ngAfterViewInit() {
    this.dataSourcePaymentMethods.sort = this.sortPaymentMethodsTable;
    this.dataSourceClients.sort = this.sortClientsTable;
    this.dataSourcePaymentMethods.paginator = this.paginatorPaymentMethodsTable;
    this.dataSourceClients.paginator = this.paginatorClientsTable;

  }

  getPaymentMethods() {
    this.paymentMethodService.getPaymentMethods().subscribe(
      data => {
        this.paymentMethods = data;
        this.refreshPaymentMethodsDataSource(this.paymentMethods);
      },
      (err: HttpErrorResponse) => {

      }
    );
  }

  getClients() {
    this.clientService.getClients().subscribe(
      data => {
        const currentUserId = this.authService.getUserId();
        if (currentUserId != null) {
          data = data.filter(client => client.id != currentUserId);
        }
        this.clients = data;

        this.refresClientsDataSource(this.clients);
      },
      (err: HttpErrorResponse) => {

      }
    );
  }
  refresClientsDataSource(clients: any) {
    this.dataSourceClients = new MatTableDataSource(clients);
  }

  refreshPaymentMethodsDataSource(paymentMethods: any) {
    this.dataSourcePaymentMethods = new MatTableDataSource(paymentMethods);
  }

  applyFilterPaymentMethodsTable(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourcePaymentMethods.filter = filterValue.trim().toLowerCase();

    if (this.dataSourcePaymentMethods.paginator) {
      this.dataSourcePaymentMethods.paginator.firstPage();
    }
  }

  applyFilterClientsTable(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceClients.filter = filterValue.trim().toLowerCase();
    if (this.dataSourceClients.paginator) {
      this.dataSourceClients.paginator.firstPage();
    }
  }

  edit(paymentMethod) {

  }

  remove(paymentMethod) {

  }

  showClientPaymentMethods(client) {

  }

  deactivate(client) { }

}
