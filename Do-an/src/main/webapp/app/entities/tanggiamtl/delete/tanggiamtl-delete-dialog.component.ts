import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITanggiamtl } from '../tanggiamtl.model';
import { TanggiamtlService } from '../service/tanggiamtl.service';

@Component({
  standalone: true,
  templateUrl: './tanggiamtl-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TanggiamtlDeleteDialogComponent {
  tanggiamtl?: ITanggiamtl;

  protected tanggiamtlService = inject(TanggiamtlService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tanggiamtlService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
