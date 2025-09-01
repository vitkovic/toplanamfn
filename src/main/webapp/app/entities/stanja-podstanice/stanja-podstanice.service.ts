import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';

const baseApiUrl = 'api/stanja-podstanices';
const uploadApiUrl = 'api/nstanice-upload';

export default class StanjaPodstaniceService {
  public find(id: number): Promise<IStanjaPodstanice> {
    return new Promise<IStanjaPodstanice>((resolve, reject) => {
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

 

  public vratiPraznaStanja(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/vrati-prazna')
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

  public create(entity: IStanjaPodstanice): Promise<IStanjaPodstanice> {
    return new Promise<IStanjaPodstanice>((resolve, reject) => {
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

  public createBatch(entity: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}` + '/batch', entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IStanjaPodstanice): Promise<IStanjaPodstanice> {
    return new Promise<IStanjaPodstanice>((resolve, reject) => {
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
  
  public sendFile(entity: FormData): Promise<any> {
      return new Promise<any>((resolve, reject) => {
        axios
          .post(`${uploadApiUrl}`, entity, {headers: {
            "Content-Type": "multipart/form-data"
          }})
          .then(res => {
            resolve(res.data);
          })
          .catch(err => {
            reject(err);
          });
      });
    }
  
  
}
