import axios from 'axios';

import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';

const baseApiUrl = 'api/stanja-podstanice-za-racuns';

export default class StanjaPodstaniceZaRacunService {
  public find(id: number): Promise<IStanjaPodstaniceZaRacun> {
    return new Promise<IStanjaPodstaniceZaRacun>((resolve, reject) => {
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

  public create(entity: IStanjaPodstaniceZaRacun): Promise<IStanjaPodstaniceZaRacun> {
    return new Promise<IStanjaPodstaniceZaRacun>((resolve, reject) => {
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

  public update(entity: IStanjaPodstaniceZaRacun): Promise<IStanjaPodstaniceZaRacun> {
    return new Promise<IStanjaPodstaniceZaRacun>((resolve, reject) => {
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
