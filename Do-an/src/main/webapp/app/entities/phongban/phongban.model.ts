export interface IPhongban {
  id: number;
  mapb?: string | null;
  tenpb?: string | null;
  sdt?: number | null;
}

export type NewPhongban = Omit<IPhongban, 'id'> & { id: null };
