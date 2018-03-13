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

export interface PrimaryTransaction {}

@Component({
  selector: 'app-primary-transaction',
  templateUrl: './primary-transaction.component.html',
  styleUrls: ['./primary-transaction.component.css']
})

export class PrimaryTransactionComponent implements OnInit {

  username: string;
  title = 'app';
  myData: Array < any > ;
  dataSource: PrimaryTransactionDataSource;
  displayedColumns = ['postDate', 'description', 'type', 'status', 'amount', 'availableBalance'];

	constructor(private route: ActivatedRoute, private userService: UserService) {
		this.route.params.forEach((params: Params) => {
     		this.username = params['username'];
		});

		this.getPrimaryTransactionList();
	}

	getPrimaryTransactionList() {
		this.userService.getPrimaryTransactionList(this.username).subscribe(
			res => {
				this.myData = res;
				this.dataSource = new PrimaryTransactionDataSource(this.myData)
      },
      error => console.log(error)
		)
	}

	ngOnInit() {}
}

export class PrimaryTransactionDataSource extends DataSource<any> {
  constructor(private data: PrimaryTransaction[]) {
    super();
  }

  connect(): Observable<PrimaryTransaction[]> {
    return Observable.of(this.data);
  }

  disconnect() {}

}