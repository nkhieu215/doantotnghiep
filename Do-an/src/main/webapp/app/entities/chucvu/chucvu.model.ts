export interface IChucvu {
  id: number;
  macv?: string | null;
  tencv?: string | null;
  hcpccv?: number | null;
}

export type NewChucvu = Omit<IChucvu, 'id'> & { id: null };
