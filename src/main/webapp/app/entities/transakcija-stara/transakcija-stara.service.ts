import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';

const baseApiUrl = 'api/transakcija-staras';

export default class TransakcijaStaraService {
  public find(id: number): Promise<ITransakcijaStara> {
    return new Promise<ITransakcijaStara>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ITransakcijaStara): Promise<ITransakcijaStara> {
    return new Promise<ITransakcijaStara>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ITransakcijaStara): Promise<ITransakcijaStara> {
    return new Promise<ITransakcijaStara>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
