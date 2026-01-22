import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IVlasnik } from '@/shared/model/vlasnik.model';
const baseApiUrlCriteria = 'api/vlasniks/criteria';
const baseApiUrl = 'api/vlasniks';
const baseApiUrlall = 'api/vlasniksall';

export default class VlasnikService {
  public find(id: number): Promise<IVlasnik> {
    return new Promise<IVlasnik>((resolve, reject) => {
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
  
  public retrieveAll(paginationQuery?: any): Promise<any> {
      return new Promise<any>((resolve, reject) => {
        axios
          .get(baseApiUrlall)
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

  public create(entity: IVlasnik): Promise<IVlasnik> {
    return new Promise<IVlasnik>((resolve, reject) => {
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

  public update(entity: IVlasnik): Promise<IVlasnik> {
    return new Promise<IVlasnik>((resolve, reject) => {
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
  public retrieveCriteria(search, paginationQuery?: any): Promise<any> {
     return new Promise<any>((resolve, reject) => {
       axios
         .post(baseApiUrlCriteria + `?${buildPaginationQueryOpts(paginationQuery)}`, search)        
         .then(res => {
           resolve(res);
         })
         .catch(err => {
           reject(err);
         });
     });
   }
}
