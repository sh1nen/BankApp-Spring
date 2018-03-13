import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { DataSource } from '@angular/cdk/table';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { Http, Response, RequestOptions, Headers, Request, RequestMethod } from '@angular/http';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';

import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})

export class UserAccountComponent implements OnInit {

  title = 'app';
  myData: Array < any > ;
  dataSource: UserDataSource;
  displayedColumns = ['username', 'firstname', 'lastname', 'email', 'phone', 'primaryAccount', 'savingsAccount', 'enabled'];

  constructor(private userService: UserService, private router: Router) {
    this.getUsers();
  }

	getUsers() {
		this.userService.getUsers().subscribe(
			res => {
        this.myData = res;
        this.dataSource = new UserDataSource(this.myData)
      },
      error => console.log(error)
		)
	}

	onSelectPrimary(username: string) {
    	this.router.navigate(['/primaryTransaction', username]);
  	}	

  	onSelectSavings(username: string) {
    	this.router.navigate(['/savingsTransaction', username]);
  	}	

  	enableUser(username: string) {
  		this.userService.enableUser(username).subscribe();
  		location.reload();
  	}

  	disableUser(username: string) {
  		this.userService.disableUser(username).subscribe();
  		location.reload();
  	}

    ngOnInit() {}
}

export class UserDataSource extends DataSource<any> {
  constructor(private data: User[]) {
    super();
  }

  connect(): Observable<User[]> {
    return Observable.of(this.data);
  }

  disconnect() {}

  }