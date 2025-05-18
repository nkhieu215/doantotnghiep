import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IThuetn } from '../thuetn.model';
import { ThuetnService } from '../service/thuetn.service';

@Component({
  standalone: true,
  templateUrl: './thuetn-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ThuetnDeleteDialogComponent {
  thuetn?: IThuetn;

  protected thuetnService = inject(ThuetnService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thuetnService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
