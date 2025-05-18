import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IThamsotl } from '../thamsotl.model';

@Component({
  standalone: true,
  selector: 'jhi-thamsotl-detail',
  templateUrl: './thamsotl-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ThamsotlDetailComponent {
  @Input() thamsotl: IThamsotl | null = null;

  previousState(): void {
    window.history.back();
  }
}
