// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';

//Dodao Dragan
import Notifications from 'vue-notification';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

/* tslint:disable */

import StanService from '@/entities/stan/stan.service';
import VlasnikService from '@/entities/vlasnik/vlasnik.service';
import TipPotrosacaService from '@/entities/tip-potrosaca/tip-potrosaca.service';
import PodstanicaService from '@/entities/podstanica/podstanica.service';
import StanjaPodstaniceService from '@/entities/stanja-podstanice/stanja-podstanice.service';
import TransakcijaService from '@/entities/transakcija/transakcija.service';
import CeneService from '@/entities/cene/cene.service';
import VrstaTransakcijeService from '@/entities/vrsta-transakcije/vrsta-transakcije.service';
import SifraDokumentaService from '@/entities/sifra-dokumenta/sifra-dokumenta.service';
import SifraPromeneService from '@/entities/sifra-promene/sifra-promene.service';
import NacrtRacunaService from '@/entities/nacrt-racuna/nacrt-racuna.service';
import RacunService from '@/entities/racun/racun.service';
import IzvodService from '@/entities/izvod/izvod.service';
import StavkeIzvodaService from '@/entities/stavke-izvoda/stavke-izvoda.service';
import StavkeIzvodaPostanskaService from '@/entities/stavke-izvoda-postanska/stavke-izvoda-postanska.service';
import TransakcijaStaraService from '@/entities/transakcija-stara/transakcija-stara.service';
import CeneStareService from '@/entities/cene-stare/cene-stare.service';
import ParametriService from '@/entities/parametri/parametri.service';
import ParametriIstorijaService from '@/entities/parametri-istorija/parametri-istorija.service';
import OpomenaService from '@/entities/opomena/opomena.service';
import OstaliRacuniService from '@/entities/ostali-racuni/ostali-racuni.service';
import UtuzenjeService from '@/entities/utuzenje/utuzenje.service';
import StavkeUtuzenjaService from '@/entities/stavke-utuzenja/stavke-utuzenja.service';
import UgovorRateService from '@/entities/ugovor-rate/ugovor-rate.service';
import StanjaPodstaniceZaRacunService from '@/entities/stanja-podstanice-za-racun/stanja-podstanice-za-racun.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);

//dodao dragan

import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/locale/sr';

import { MonthPicker } from 'vue-month-picker';
import { MonthPickerInput } from 'vue-month-picker';




Vue.component('MonthPicker', MonthPicker);
Vue.component('MonthPickerInput', MonthPickerInput);
Vue.use(MonthPicker)
Vue.use(MonthPickerInput)


Vue.use(Notifications);
Vue.component('DatePicker', DatePicker);
Vue.use(DatePicker);







Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    stanService: () => new StanService(),
    vlasnikService: () => new VlasnikService(),
    tipPotrosacaService: () => new TipPotrosacaService(),
    podstanicaService: () => new PodstanicaService(),
    stanjaPodstaniceService: () => new StanjaPodstaniceService(),
    transakcijaService: () => new TransakcijaService(),
    ceneService: () => new CeneService(),
    vrstaTransakcijeService: () => new VrstaTransakcijeService(),
    sifraDokumentaService: () => new SifraDokumentaService(),
    sifraPromeneService: () => new SifraPromeneService(),
    nacrtRacunaService: () => new NacrtRacunaService(),
    racunService: () => new RacunService(),
    izvodService: () => new IzvodService(),
    stavkeIzvodaService: () => new StavkeIzvodaService(),
    stavkeIzvodaPostanskaService: () => new StavkeIzvodaPostanskaService(),
    transakcijaStaraService: () => new TransakcijaStaraService(),
    ceneStareService: () => new CeneStareService(),
    parametriService: () => new ParametriService(),
    parametriIstorijaService: () => new ParametriIstorijaService(),
    opomenaService: () => new OpomenaService(),
    ostaliRacuniService: () => new OstaliRacuniService(),
    utuzenjeService: () => new UtuzenjeService(),
    stavkeUtuzenjaService: () => new StavkeUtuzenjaService(),
    ugovorRateService: () => new UgovorRateService(),
    stanjaPodstaniceZaRacunService: () => new StanjaPodstaniceZaRacunService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
  },
  i18n,
  store,
});
