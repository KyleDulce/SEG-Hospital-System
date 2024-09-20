import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'visualize-search',
  templateUrl: './visualize-search.component.html',
  styleUrls: ['./visualize-search.component.css']
})
export class VisualizeSearchComponent {
  @Output()
  divisionChange = new EventEmitter<string | undefined>();

  @Input()
  division?: string = '';

  onDivisionChange(value: string) {
    console.log("change " + value)
    this.division = value;
    this.divisionChange.emit(value)
  }
}
