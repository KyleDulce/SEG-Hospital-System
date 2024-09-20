import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SimpleDivision } from '../../shared/model/visualize-divisions.models';
import { VisualizeDivisionService } from '../visualize-division.service';

@Component({
  selector: 'visualize-browse',
  templateUrl: './visualize-browse.component.html',
  styleUrls: ['./visualize-browse.component.css']
})
export class VisualizeBrowseComponent implements OnInit {

  @Input()
  public selected?: string;

  @Output()
  public selectedChange = new EventEmitter<string | undefined>();

  data: Array<SimpleDivision> = []
  columnDef: Array<String> = ['divisionId', 'divisionName']

  constructor(
    private visualizeDivisionService: VisualizeDivisionService
  ) {}

  ngOnInit(): void {
    this.visualizeDivisionService.getAllDivisions()
      .subscribe({
        next: resposne => {
          this.data = resposne.divisions
        }
      })
  }

  onSelectChange(value: number): void {
    this.selected = this.data[value].divisionId;
    this.selectedChange.emit(this.selected);
  }
}
