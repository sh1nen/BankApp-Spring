import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Params } from '@angular/router';
import { DataSource } from '@angular/cdk/table';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { Http, Response, RequestOptions, Headers, Request, RequestMethod } from '@angular/http';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';

export interface SavingsTransaction {}

@Component({
  selector: 'app-savings-transaction',
  templateUrl: './savings-transaction.component.html',
  styleUrls: ['./savings-transaction.component.css']
})

export class SavingsTransactionComponent implements OnInit {

  username: string;
  title = 'app';
  myData: Array < any > ;
  dataSource: SavingsTransactionDataSource;
  displayedColumns = ['postDate', 'description', 'type', 'status', 'amount', 'availableBalance'];

	constructor(private route: ActivatedRoute, private userService: UserService) {
		this.route.params.forEach((params: Params) => {
     		this.username = params['username'];
		});

		this.getSavingsTransactionList();
	}

	getSavingsTransactionList() {
		this.userService.getSavingsTransactionList(this.username).subscribe(
			res => {
				this.myData = res;
				this.dataSource = new SavingsTransactionDataSource(this.myData)
      },
      error => console.log(error)
		)
	}

	ngOnInit() {}
}

export class SavingsTransactionDataSource extends DataSource<any> {
  constructor(private data: SavingsTransaction[]) {
    super();
  }

  connect(): Observable<SavingsTransaction[]> {
    return Observable.of(this.data);
  }

  disconnect() {}

}