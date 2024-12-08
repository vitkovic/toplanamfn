import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRacun } from '@/shared/model/racun.model';

const baseApiUrl = 'api/racuns';
const baseApiUrlCriteria = 'api/racuns/criteria';
const baseApiUrlStampanje = 'api/racuni-stampanje';
const baseApiUrlCreateBankFiles = 'api/racuni-kreiranje-datoteke-banka';

export default class RacunService {
  public find(id: number): Promise<IRacun> {
    return new Promise<IRacun>((resolve, reject) => {
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

  public stampanje(search): Promise<any> {
    return new Promise<any>((resolve, reject) => { 
      axios
        .post(`${baseApiUrlStampanje}`, search, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public createBankFiles(mesec): Promise<any> {
    return new Promise<any>((resolve, reject) => { 
      axios
        .post(`${baseApiUrlCreateBankFiles}`, mesec, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public stampanjeZaJedanRacun(id:number): Promise<any> {
    return new Promise<any>((resolve, reject) => { 
      axios
        .get(`${baseApiUrlStampanje}/${id}`, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
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

  public create(entity: IRacun): Promise<IRacun> {
    return new Promise<IRacun>((resolve, reject) => {
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

  public update(entity: IRacun): Promise<IRacun> {
    return new Promise<IRacun>((resolve, reject) => {
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
