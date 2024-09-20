import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { GetDivisionResponse } from '../../shared/model/visualize-divisions.models';
import { VisualizeDivisionService } from '../visualize-division.service';

@Component({
  selector: 'visualize-info',
  templateUrl: './visualize-info.component.html',
  styleUrls: ['./visualize-info.component.css']
})
export class VisualizeInfoComponent implements OnChanges {

  @Input()
  public selectedDivision?: string;

  public isLoading: boolean = false;
  public division?: GetDivisionResponse;

  constructor(
    private visualizeDivisionService: VisualizeDivisionService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['selectedDivision'] && this.selectedDivision) {
      this.isLoading = true;
      this.division = undefined;
      this.visualizeDivisionService.getDivisionById(this.selectedDivision)
        .subscribe({
          next: response => {
            this.division = response
            this.isLoading = false
          },
          error: err => {
            console.error(err)
            this.isLoading = false
          }
        })
    }
  }

  bedColumnDef: Array<String> = ["roomNum", "bedNum", "isOccupied"]
  reqColumnDef: Array<String> = ["patientId", "priority"]
}
