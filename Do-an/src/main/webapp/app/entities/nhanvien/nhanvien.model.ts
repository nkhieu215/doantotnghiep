import dayjs from 'dayjs/esm';
import { IChucvu } from 'app/entities/chucvu/chucvu.model';
import { IPhongban } from 'app/entities/phongban/phongban.model';

export interface INhanvien {
  id: number;
  manv?: string | null;
  hoten?: string | null;
  ngaysinh?: dayjs.Dayjs | null;
  gioitinh?: string | null;
  quequan?: string | null;
  diachi?: string | null;
  hsluong?: number | null;
  msthue?: number | null;
  chucvu?: IChucvu | null;
  phongban?: IPhongban | null;
}

export type NewNhanvien = Omit<INhanvien, 'id'> & { id: null };
