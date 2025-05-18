import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPhongban } from '../phongban.model';
import { PhongbanService } from '../service/phongban.service';

@Component({
  standalone: true,
  templateUrl: './phongban-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PhongbanDeleteDialogComponent {
  phongban?: IPhongban;

  protected phongbanService = inject(PhongbanService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phongbanService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
