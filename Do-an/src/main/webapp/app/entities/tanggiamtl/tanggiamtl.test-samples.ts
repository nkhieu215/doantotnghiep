import { ITanggiamtl, NewTanggiamtl } from './tanggiamtl.model';

export const sampleWithRequiredData: ITanggiamtl = {
  id: 923,
};

export const sampleWithPartialData: ITanggiamtl = {
  id: 26040,
  ngaythang: 'yippee turret boldly',
  tkn: 4868,
  tkc: 30860,
  diengiai: 'quaintly',
};

export const sampleWithFullData: ITanggiamtl = {
  id: 21046,
  ngaythang: 'ah',
  tkn: 5897,
  tkc: 25,
  sotien: 29206.04,
  diengiai: 'even scupper flugelhorn',
};

export const sampleWithNewData: NewTanggiamtl = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
