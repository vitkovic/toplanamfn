import axios from 'axios';

import { ISifraPromene } from '@/shared/model/sifra-promene.model';

const baseApiUrl = 'api/sifra-promenes';
const baseApiUrlZaduzenje = 'api/sifra-promenes/zaduzenje';
const baseApiUrlRazduzenje = 'api/sifra-promenes/razduzenje';

export default class SifraPromeneService {
  public find(id: number): Promise<ISifraPromene> {
    return new Promise<ISifraPromene>((resolve, reject) => {
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

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveZaduzenje(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlZaduzenje)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveRazduzenje(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlRazduzenje)
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

  public create(entity: ISifraPromene): Promise<ISifraPromene> {
    return new Promise<ISifraPromene>((resolve, reject) => {
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

  public update(entity: ISifraPromene): Promise<ISifraPromene> {
    return new Promise<ISifraPromene>((resolve, reject) => {
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
