import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IThamsotl } from '../thamsotl.model';
import { ThamsotlService } from '../service/thamsotl.service';

@Component({
  standalone: true,
  templateUrl: './thamsotl-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ThamsotlDeleteDialogComponent {
  thamsotl?: IThamsotl;

  protected thamsotlService = inject(ThamsotlService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thamsotlService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
