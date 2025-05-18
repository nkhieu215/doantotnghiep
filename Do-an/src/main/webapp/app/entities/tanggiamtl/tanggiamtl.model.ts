import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';

export interface ITanggiamtl {
  id: number;
  ngaythang?: string | null;
  tkn?: number | null;
  tkc?: number | null;
  sotien?: number | null;
  diengiai?: string | null;
  nhanvien?: INhanvien | null;
}

export type NewTanggiamtl = Omit<ITanggiamtl, 'id'> & { id: null };
