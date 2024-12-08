import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITransakcija } from '@/shared/model/transakcija.model';

const baseApiUrl = 'api/transakcijas';
const baseApiUrlCriteria = 'api/transakcijas-criteria';
const baseApiUrlCriteriaAnalitickiDnevnik = 'api/transakcijas-criteria-analiticki-dnevnik';
const baseApiUrlCriteriaAnalitickiDnevnikStampanje = 'api/transakcijas-criteria-analiticki-dnevnik-stampanje';
const baseApiUrlCriteriaRekapitulacijaSifraPromeneDatum = 'api/transakcijas-rekapitulacija-sifra-promene-datum';
const baseApiUrlRekapitulacijaSifrePromeneDatumStampanje = 'api/transakcijas-rekapitulacija-sifra-promene-datum-stampanje';
const baseApiUrlAnalitickaKarticaStampanje = 'api/transakcijas/sve-prikaz-stampanje';
const baseApiUrlSintetickiDnevnik = 'api/transakcijas-sinteticki-dnevnik';
const baseApiUrlSintetickiDnevnikStampanje = 'api/transakcijas-sinteticki-dnevnik-stampanje';

export default class TransakcijaService {
  public find(id: number): Promise<ITransakcija> {
    return new Promise<ITransakcija>((resolve, reject) => {
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

  public retrieveCriteriaAnalitickiDnevnik(search ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlCriteriaAnalitickiDnevnik, search)        
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public rekapitulacijaSifraPromeneDatum(search ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlCriteriaRekapitulacijaSifraPromeneDatum, search)        
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public sintetickiDnevnik(search ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlSintetickiDnevnik, search)        
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  public sintetickiDnevnikStampanje(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlSintetickiDnevnikStampanje, search, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveCriteriaAnalitickiDnevnikStampanje(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlCriteriaAnalitickiDnevnikStampanje, search, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public analitickaKarticaStampanje(search): Promise<any> {
    debugger
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlAnalitickaKarticaStampanje,  search, {responseType:'blob'})
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveRekapitulacijaSifrePromeneDatumStampanje(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlRekapitulacijaSifrePromeneDatumStampanje, search, {responseType:'blob'})
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

  public findAllForStan(sifra?: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/sve-prikaz/' + sifra)
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

  public create(entity: ITransakcija): Promise<ITransakcija> {
    return new Promise<ITransakcija>((resolve, reject) => {
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

  
  public update(entity: ITransakcija): Promise<ITransakcija> {
    return new Promise<ITransakcija>((resolve, reject) => {
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
