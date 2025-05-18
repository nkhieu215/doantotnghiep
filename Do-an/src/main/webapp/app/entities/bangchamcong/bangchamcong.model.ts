import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';

export interface IBangchamcong {
  id: number;
  ncdilam?: number | null;
  thangcc?: string | null;
  nclephep?: number | null;
  xeploai?: string | null;
  ngayththuong?: number | null;
  ngaythle?: number | null;
  nhanvien?: INhanvien | null;
}

export type NewBangchamcong = Omit<IBangchamcong, 'id'> & { id: null };
