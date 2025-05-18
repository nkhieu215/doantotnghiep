export interface IThuetn {
  id: number;
  bacthue?: string | null;
  tu?: number | null;
  den?: number | null;
  thuesuat?: number | null;
}

export type NewThuetn = Omit<IThuetn, 'id'> & { id: null };
