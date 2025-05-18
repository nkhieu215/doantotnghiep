import dayjs from 'dayjs/esm';

import { INhanvien, NewNhanvien } from './nhanvien.model';

export const sampleWithRequiredData: INhanvien = {
  id: 23563,
};

export const sampleWithPartialData: INhanvien = {
  id: 30867,
  ngaysinh: dayjs('2025-04-12T13:58'),
  msthue: 25954,
};

export const sampleWithFullData: INhanvien = {
  id: 29649,
  manv: 'woot upend down',
  hoten: 'limping',
  ngaysinh: dayjs('2025-04-13T11:03'),
  gioitinh: 'villainous perennial and',
  quequan: 'nonstop cartload',
  diachi: 'happily on',
  hsluong: 26432,
  msthue: 30279,
};

export const sampleWithNewData: NewNhanvien = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
