import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IChucvu } from '../chucvu.model';
import { ChucvuService } from '../service/chucvu.service';

@Component({
  standalone: true,
  templateUrl: './chucvu-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ChucvuDeleteDialogComponent {
  chucvu?: IChucvu;

  protected chucvuService = inject(ChucvuService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chucvuService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
