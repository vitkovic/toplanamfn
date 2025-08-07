import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IIzvod } from '@/shared/model/izvod.model';

const baseApiUrl = 'api/izvods';
const baseApiPostanskaUrl = 'api/izvod-postanskas';
const uploadApiUrl = 'api/izvods-upload';
const uploadApiPostanskaUrl = 'api/izvod-postanskas-upload';

export default class IzvodService {
  public find(id: number): Promise<IIzvod> {
    return new Promise<IIzvod>((resolve, reject) => {
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
  
  
  public knjiziIzvod(id: number): Promise<IIzvod> {
      return new Promise<IIzvod>((resolve, reject) => {
        axios
          .get(`${baseApiUrl}knjizi/${id}`)
          .then(res => {
            resolve(res.data);
          })
          .catch(err => {
            reject(err);
          });
      });
    }
  
	public rasknjiziIzvod(id: number): Promise<IIzvod> {
	      return new Promise<IIzvod>((resolve, reject) => {
	        axios
	          .get(`${baseApiUrl}rasknjizi/${id}`)
	          .then(res => {
	            resolve(res.data);
	          })
	          .catch(err => {
	            reject(err);
	          });
	      });
	}

  public findPostanska(id: number): Promise<IIzvod> {
    return new Promise<IIzvod>((resolve, reject) => {
      axios
        .get(`${baseApiPostanskaUrl}/${id}`)
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

  public retrievePostanska(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiPostanskaUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
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

  public deletePostanska(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiPostanskaUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IIzvod): Promise<IIzvod> {
    return new Promise<IIzvod>((resolve, reject) => {
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

  public sendFilePostanska(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${uploadApiPostanskaUrl}`, entity, {headers: {
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

  public update(entity: IIzvod): Promise<IIzvod> {
    return new Promise<IIzvod>((resolve, reject) => {
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
