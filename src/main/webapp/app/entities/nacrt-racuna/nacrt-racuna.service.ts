import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';

const baseApiUrl = 'api/nacrt-racunas';
const basePriprema = 'api/racuniPriprema';
const baseStampanje = 'api/stampanje';
const baseMail = 'api/smail';
const baseApiUrlRekapitulacija = "api/racuni-rekapitulacija-po-pdv";
const baseStampanjeRekapitulacija = "api/racuni-rekapitulacija-po-pdv-stampanje";

export default class NacrtRacunaService {
  public find(id: number): Promise<INacrtRacuna> {
    return new Promise<INacrtRacuna>((resolve, reject) => {
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

  public rekapitulacijaPdv(nacrtRacunaId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlRekapitulacija}/${nacrtRacunaId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public stampanje(nacrtRacunaId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseStampanje}/${nacrtRacunaId}`, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
  public smail(nacrtRacunaId: number): Promise<any> {
     return new Promise<any>((resolve, reject) => {
       axios
         .get(`${baseStampanje}/${nacrtRacunaId}`)
         .then(res => {
           resolve(res.data);
         })
         .catch(err => {
           reject(err);
         });
     });
   }

  public stampanjeRekapitulacije(nacrtRacunaId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseStampanjeRekapitulacija}/${nacrtRacunaId}`, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public pripremaRacuna(): Promise<any> {
    return new Promise<any>((resolve, reject) => { 
      axios
        .get(basePriprema)
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

  public create(entity: INacrtRacuna): Promise<INacrtRacuna> {
    return new Promise<INacrtRacuna>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
          //resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: INacrtRacuna): Promise<INacrtRacuna> {
    return new Promise<INacrtRacuna>((resolve, reject) => {
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

  public knjizenje(entity: INacrtRacuna): Promise<INacrtRacuna> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put('api/nacrtRacunaKnjizenje', entity)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  

}
