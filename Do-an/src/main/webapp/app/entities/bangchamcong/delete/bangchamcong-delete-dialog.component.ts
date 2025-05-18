import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBangchamcong } from '../bangchamcong.model';
import { BangchamcongService } from '../service/bangchamcong.service';

@Component({
  standalone: true,
  templateUrl: './bangchamcong-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BangchamcongDeleteDialogComponent {
  bangchamcong?: IBangchamcong;

  protected bangchamcongService = inject(BangchamcongService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bangchamcongService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
