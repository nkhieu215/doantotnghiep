import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';

export interface IThamsotl {
  id: number;
  thangnam?: string | null;
  ncchuan?: number | null;
  giocchuan?: number | null;
  hsgioth?: number | null;
  hsgiole?: number | null;
  pcan?: number | null;
  tlbhxh?: number | null;
  tlbhyt?: number | null;
  tlbhtn?: string | null;
  tlkpcd?: number | null;
  nhanvien?: INhanvien | null;
}

export type NewThamsotl = Omit<IThamsotl, 'id'> & { id: null };
