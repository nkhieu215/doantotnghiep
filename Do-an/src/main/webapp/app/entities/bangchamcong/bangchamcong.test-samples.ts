import { IBangchamcong, NewBangchamcong } from './bangchamcong.model';

export const sampleWithRequiredData: IBangchamcong = {
  id: 13580,
};

export const sampleWithPartialData: IBangchamcong = {
  id: 31536,
  ncdilam: 17022,
  thangcc: 'however skate wrong',
  xeploai: 'while depersonalise',
  ngayththuong: 20694,
  ngaythle: 23361,
};

export const sampleWithFullData: IBangchamcong = {
  id: 27595,
  ncdilam: 7803,
  thangcc: 'so modernise',
  nclephep: 7211,
  xeploai: 'besides',
  ngayththuong: 15422,
  ngaythle: 11176,
};

export const sampleWithNewData: NewBangchamcong = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
