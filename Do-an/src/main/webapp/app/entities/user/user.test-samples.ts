import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 32399,
  login: 'DjGYj',
};

export const sampleWithPartialData: IUser = {
  id: 17184,
  login: 'vXlQ',
};

export const sampleWithFullData: IUser = {
  id: 6615,
  login: 'Lo.Phb',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
