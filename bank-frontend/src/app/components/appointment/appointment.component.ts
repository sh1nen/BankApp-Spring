import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { DataSource } from '@angular/cdk/table';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { Http, Response, RequestOptions, Headers, Request, RequestMethod } from '@angular/http';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';

export interface Appointment {}

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit {

  appointmentList: Object[];
  title = 'app';
  myData: Array < any > ;
  dataSource: AppointmentDataSource;
  displayedColumns = ['id', 'username', 'date', 'description', 'confirmed'];

    constructor(private appointmentService: AppointmentService) {
      this.getAppointmentList();
    }
  
    getAppointmentList() {
      this.appointmentService.getAppointmentList().subscribe(
        res => {
          this.myData = res;
          this.dataSource = new AppointmentDataSource(this.myData)
        },
        error => console.log(error)
      )
    }	
  
    confirmAppointment(id: number) {
        this.appointmentService.confirmAppointment(id).subscribe();
        location.reload();
      }
  
   ngOnInit() {}
}

export class AppointmentDataSource extends DataSource<any> {
  constructor(private data: Appointment[]) {
    super();
  }

  connect(): Observable<Appointment[]> {
    return Observable.of(this.data);
  }

  disconnect() {}

}