// @ts-nocheck
import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import StavkeUtuzenjaService from './stavke-utuzenja.service';

import TransakcijaService from '@/entities/transakcija/transakcija.service';
import { ITransakcija } from '@/shared/model/transakcija.model';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StavkeUtuzenja extends mixins(AlertMixin) {
  @Inject('stavkeUtuzenjaService') private stavkeUtuzenjaService: () => StavkeUtuzenjaService;
  
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  
  public transakcije: ITransakcija[] = [];

  public transakcijeFields = [
      { key: 'datum', label: 'Datum' },
      { key: 'sifraPromene.sifra', label: 'Šifra promene' },
      { key: 'opis', label: 'Opis' },
      { key: 'duguje', label: 'Duguje' },
      { key: 'potrazuje', label: 'Potražuje' }
  ];
  
  private removeId: number = null;

  public stavkeUtuzenjas: IStavkeUtuzenja[] = [];

  public isFetching = false;
  
  
  public trenutnaStavkaOpis = '';

  public mounted(): void {
    this.retrieveAllStavkeUtuzenjas();
  }

  public clear(): void {
    this.retrieveAllStavkeUtuzenjas();
  }

  public retrieveAllStavkeUtuzenjas(): void {
    this.isFetching = true;

    this.stavkeUtuzenjaService()
      .retrieve()
      .then(
        res => {
          this.stavkeUtuzenjas = res.data;
		  console.log(res.data);
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStavkeUtuzenja): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStavkeUtuzenja(): void {
    this.stavkeUtuzenjaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.stavkeUtuzenja.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllStavkeUtuzenjas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
  
  public prikaziTransakcije(stavka): void {
	  
	  this.trenutnaStavkaOpis = stavka.opis || '';
	
      this.transakcijaService()
          .findByStavkaUtuzenja(stavka.id)
          .then(res => {
			
			const ukupnoDuguje = res.data.reduce((s, t) => s + Number(t.duguje || 0), 0);
			const ukupnoPotrazuje = res.data.reduce((s, t) => s + Number(t.potrazuje || 0), 0);

			this.transakcije = res.data;
			console.log(this.transakcije)
			this.transakcije.push({
			  opis: 'Ukupno:',
			  duguje: ukupnoDuguje,
			  potrazuje: ukupnoPotrazuje,
			  isTotal: true
			});
		      (<any>this.$refs.transakcijeModal).show();
          });

  }
  
  get ukupnoDuguje(): number {
      return this.transakcije.reduce((s, t) => s + (t.duguje || 0), 0);
  }

  get ukupnoPotrazuje(): number {
      return this.transakcije.reduce((s, t) => s + (t.potrazuje || 0), 0);
  }
  public exportTransakcijeCsv(): void {
      const rows = this.transakcije || [];

      const header = ['Datum', 'Šifra promene', 'Opis Transakcije', 'Duguje', 'Potražuje', 'Opis Zaduzenja', 'Sifra Stana'];

	  const csvRows = rows.map(t => [
	      t.datum || '',
	      t.sifraPromene?.sifra || '',
	      (t.opis || '').replace(/[\r\n]+/g, ' '),
	      t.duguje ?? '',
	      t.potrazuje ?? '',
		  t.stavkaUtuzenja?.opis ?? '',
		  t.stan?.sifra ?? '',
	  ]);

	  const csvContent = [header, ...csvRows]
	      .map(row => row.join(','))
	      .join('\r\n');

      const blob = new Blob(['\ufeff' + csvContent], {
          type: 'text/csv;charset=utf-8;'
      });

      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.setAttribute('download', 'povezane-transakcije.csv');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
  }
}
