import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBangchamcong } from '../bangchamcong.model';

@Component({
  standalone: true,
  selector: 'jhi-bangchamcong-detail',
  templateUrl: './bangchamcong-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BangchamcongDetailComponent {
  @Input() bangchamcong: IBangchamcong | null = null;

  previousState(): void {
    window.history.back();
  }
}
