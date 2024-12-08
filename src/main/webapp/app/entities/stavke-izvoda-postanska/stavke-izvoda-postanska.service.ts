import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

const baseApiUrl = 'api/stavke-izvoda-postanskas';
const baseApiUrlKnjizenje = 'api/stavke-izvoda-postanskas-knjizenje';

export default class StavkeIzvodaService {
  public find(id: number): Promise<IStavkeIzvoda> {
    return new Promise<IStavkeIzvoda>((resolve, reject) => {
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

  public create(entity: IStavkeIzvoda): Promise<IStavkeIzvoda> {
    return new Promise<IStavkeIzvoda>((resolve, reject) => {
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

  public update(entity: IStavkeIzvoda): Promise<IStavkeIzvoda> {
    return new Promise<IStavkeIzvoda>((resolve, reject) => {
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

  public knjizenje(entity: IStavkeIzvoda): Promise<IStavkeIzvoda> {
    return new Promise<IStavkeIzvoda>((resolve, reject) => {
      axios
        .put(`${baseApiUrlKnjizenje}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
