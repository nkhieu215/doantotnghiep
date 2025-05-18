import { IThuetn, NewThuetn } from './thuetn.model';

export const sampleWithRequiredData: IThuetn = {
  id: 23594,
};

export const sampleWithPartialData: IThuetn = {
  id: 9936,
  bacthue: 'rigidly',
  den: 1659,
};

export const sampleWithFullData: IThuetn = {
  id: 8188,
  bacthue: 'inborn hopelessly around',
  tu: 24027,
  den: 31209,
  thuesuat: 22769.52,
};

export const sampleWithNewData: NewThuetn = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
