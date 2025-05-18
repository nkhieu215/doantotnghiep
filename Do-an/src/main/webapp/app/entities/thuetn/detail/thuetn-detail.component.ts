import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IThuetn } from '../thuetn.model';

@Component({
  standalone: true,
  selector: 'jhi-thuetn-detail',
  templateUrl: './thuetn-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ThuetnDetailComponent {
  @Input() thuetn: IThuetn | null = null;

  previousState(): void {
    window.history.back();
  }
}
