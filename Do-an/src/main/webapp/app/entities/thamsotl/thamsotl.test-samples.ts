import { IThamsotl, NewThamsotl } from './thamsotl.model';

export const sampleWithRequiredData: IThamsotl = {
  id: 29887,
};

export const sampleWithPartialData: IThamsotl = {
  id: 27423,
  giocchuan: 25024,
  hsgioth: 17245.19,
  hsgiole: 27557.06,
  tlbhxh: 8984.93,
  tlkpcd: 16760.85,
};

export const sampleWithFullData: IThamsotl = {
  id: 31552,
  thangnam: 'for teaching',
  ncchuan: 13614,
  giocchuan: 29937,
  hsgioth: 12017.78,
  hsgiole: 20089.24,
  pcan: 20642,
  tlbhxh: 15923.03,
  tlbhyt: 32699.29,
  tlbhtn: 'if wrongly',
  tlkpcd: 5838.22,
};

export const sampleWithNewData: NewThamsotl = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
