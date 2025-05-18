import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IChucvu } from '../chucvu.model';

@Component({
  standalone: true,
  selector: 'jhi-chucvu-detail',
  templateUrl: './chucvu-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ChucvuDetailComponent {
  @Input() chucvu: IChucvu | null = null;

  previousState(): void {
    window.history.back();
  }
}
