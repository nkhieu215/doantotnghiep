import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPhongban } from '../phongban.model';

@Component({
  standalone: true,
  selector: 'jhi-phongban-detail',
  templateUrl: './phongban-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PhongbanDetailComponent {
  @Input() phongban: IPhongban | null = null;

  previousState(): void {
    window.history.back();
  }
}
