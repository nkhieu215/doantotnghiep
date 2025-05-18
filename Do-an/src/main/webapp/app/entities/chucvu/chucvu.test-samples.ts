import { IChucvu, NewChucvu } from './chucvu.model';

export const sampleWithRequiredData: IChucvu = {
  id: 5730,
};

export const sampleWithPartialData: IChucvu = {
  id: 31988,
  macv: 'mmm not gadzooks',
  tencv: 'bail',
};

export const sampleWithFullData: IChucvu = {
  id: 6595,
  macv: 'worth airforce usually',
  tencv: 'ack ah',
  hcpccv: 32145,
};

export const sampleWithNewData: NewChucvu = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
