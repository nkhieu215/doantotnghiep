import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITanggiamtl } from '../tanggiamtl.model';

@Component({
  standalone: true,
  selector: 'jhi-tanggiamtl-detail',
  templateUrl: './tanggiamtl-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TanggiamtlDetailComponent {
  @Input() tanggiamtl: ITanggiamtl | null = null;

  previousState(): void {
    window.history.back();
  }
}
