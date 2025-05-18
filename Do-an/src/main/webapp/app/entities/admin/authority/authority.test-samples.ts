import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'd89284e9-e806-4b69-bd3d-f65d43efbae3',
};

export const sampleWithPartialData: IAuthority = {
  name: '4d663af1-7f9f-4a27-a3b7-fb8b1a0d675f',
};

export const sampleWithFullData: IAuthority = {
  name: '19bf88d8-e7c2-4fc4-a7bc-8c95967ced1a',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
