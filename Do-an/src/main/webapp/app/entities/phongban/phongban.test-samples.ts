import { IPhongban, NewPhongban } from './phongban.model';

export const sampleWithRequiredData: IPhongban = {
  id: 21497,
};

export const sampleWithPartialData: IPhongban = {
  id: 15025,
  tenpb: 'though',
};

export const sampleWithFullData: IPhongban = {
  id: 32403,
  mapb: 'ha',
  tenpb: 'unnecessarily oof excitedly',
  sdt: 16195,
};

export const sampleWithNewData: NewPhongban = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
