import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { PaymentMethodService } from 'src/app/services/payment-method.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { PaymentMethod } from 'src/app/shared/models/payment-method';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MatTabsModule } from '@angular/material/tabs'
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { NewPmDialogComponent } from './new-payment-method-dialog/new-pm-dialog.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
	selector: 'app-client-panel',
	templateUrl: './client-panel.component.html',
	styleUrls: ['./client-panel.component.css']
})
export class ClientPanelComponent implements OnInit {

	paymentMethods: PaymentMethod[];
	noPaymentMethods: PaymentMethod[];
	displayedColumnsPaymentMethods: string[] = ['name', 'subscriptionSupported', 'actions'];
	dataSourcePaymentMethods: MatTableDataSource<any>;
	dataSourceNoPaymentMethods: MatTableDataSource<any>;

	@ViewChild(MatSort, { static: true }) sortPaymentMethodsTable: MatSort;
	@ViewChild(MatPaginator, { static: true }) paginatorPaymentMethodsTable: MatPaginator;

	@ViewChild(MatSort, { static: true }) sortNoPaymentMethodsTable: MatSort;
	@ViewChild(MatPaginator, { static: true }) paginatorNoPaymentMethodsTable: MatPaginator;

	constructor(private paymentMethodService: PaymentMethodService, private clientService: UserService,
		private authService: AuthService, private snackbarService: SnackBarService, public dialog: MatDialog) { }

	ngOnInit() {
		this.getPaymentMethods();
		this.getNoPaymentMethods();
	}

	getPaymentMethods() {
		this.paymentMethodService.getClientsPaymentMethods(this.authService.getUserId()).subscribe(
			data => {
				this.paymentMethods = data;
				this.refreshPaymentMethodsDataSource(this.paymentMethods);
			},
			(err: HttpErrorResponse) => {

			}
		);
	}

	getNoPaymentMethods() {
		this.paymentMethodService.getNoClientsPaymentMethods(this.authService.getUserId()).subscribe(
			data => {
				this.noPaymentMethods = data;
				this.refreshNoPaymentMethodsDataSource(this.noPaymentMethods);
			},
			(err: HttpErrorResponse) => {

			}
		);
	}
	refreshPaymentMethodsDataSource(paymentMethods: any) {
		this.dataSourcePaymentMethods = new MatTableDataSource(paymentMethods);
		this.dataSourcePaymentMethods.sort = this.sortPaymentMethodsTable;
		this.dataSourcePaymentMethods.paginator = this.paginatorPaymentMethodsTable;
	}
	refreshNoPaymentMethodsDataSource(noPaymentMethods: any) {
		this.dataSourceNoPaymentMethods = new MatTableDataSource(noPaymentMethods);
		this.dataSourceNoPaymentMethods.sort = this.sortNoPaymentMethodsTable;
		this.dataSourceNoPaymentMethods.paginator = this.paginatorNoPaymentMethodsTable;
	}

	remove(paymentMethod) {
		if (confirm("Are you sure you want to delete " + paymentMethod.name + " payment method?")) {
			this.paymentMethodService.deleteFromClient(this.authService.getUserId(), paymentMethod.id).subscribe(
				data => {
					let methods = this.paymentMethods.filter(method => method.id != paymentMethod.id);
					this.refreshPaymentMethodsDataSource(methods);

					this.noPaymentMethods.push(paymentMethod)
					this.refreshNoPaymentMethodsDataSource(this.noPaymentMethods);
				},
				(err: HttpErrorResponse) => {
					this.snackbarService.showMessage(err.error);
				}
			);
		}
	}

	openNewPaymentMethodDialog() {
		const dialogRef = this.dialog.open(NewPmDialogComponent, {

			data: { dataSourceNoPaymentMethods: this.dataSourceNoPaymentMethods },

			position: {
				top: '20px',
				left: '50px'
			}
		});

		dialogRef.afterClosed().subscribe(paymentMethod => {
			console.log(paymentMethod);

			var url = 'https://localhost:8762/api/' + paymentMethod.name.toLowerCase() + '/auth/clients/register/' + this.authService.getUserId();
			window.location.href = url;

		});
	}

}
