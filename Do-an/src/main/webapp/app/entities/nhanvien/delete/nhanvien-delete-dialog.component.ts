import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { INhanvien } from '../nhanvien.model';
import { NhanvienService } from '../service/nhanvien.service';

@Component({
  standalone: true,
  templateUrl: './nhanvien-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class NhanvienDeleteDialogComponent {
  nhanvien?: INhanvien;

  protected nhanvienService = inject(NhanvienService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhanvienService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
