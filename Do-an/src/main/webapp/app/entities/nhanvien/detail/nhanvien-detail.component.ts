import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { INhanvien } from '../nhanvien.model';

@Component({
  standalone: true,
  selector: 'jhi-nhanvien-detail',
  templateUrl: './nhanvien-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class NhanvienDetailComponent {
  @Input() nhanvien: INhanvien | null = null;

  previousState(): void {
    window.history.back();
  }
}
