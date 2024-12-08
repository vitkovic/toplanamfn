/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StanUpdateComponent from '@/entities/stan/stan-update.vue';
import StanClass from '@/entities/stan/stan-update.component';
import StanService from '@/entities/stan/stan.service';

import OpomenaService from '@/entities/opomena/opomena.service';

import UtuzenjeService from '@/entities/utuzenje/utuzenje.service';

import UgovorRateService from '@/entities/ugovor-rate/ugovor-rate.service';

import TransakcijaService from '@/entities/transakcija/transakcija.service';

import TransakcijaStaraService from '@/entities/transakcija-stara/transakcija-stara.service';

import RacunService from '@/entities/racun/racun.service';

import TipPotrosacaService from '@/entities/tip-potrosaca/tip-potrosaca.service';

import PodstanicaService from '@/entities/podstanica/podstanica.service';

import VlasnikService from '@/entities/vlasnik/vlasnik.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Stan Management Update Component', () => {
    let wrapper: Wrapper<StanClass>;
    let comp: StanClass;
    let stanServiceStub: SinonStubbedInstance<StanService>;

    beforeEach(() => {
      stanServiceStub = sinon.createStubInstance<StanService>(StanService);

      wrapper = shallowMount<StanClass>(StanUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stanService: () => stanServiceStub,

          opomenaService: () => new OpomenaService(),

          utuzenjeService: () => new UtuzenjeService(),

          ugovorRateService: () => new UgovorRateService(),

          transakcijaService: () => new TransakcijaService(),

          transakcijaStaraService: () => new TransakcijaStaraService(),

          racunService: () => new RacunService(),

          tipPotrosacaService: () => new TipPotrosacaService(),

          podstanicaService: () => new PodstanicaService(),

          vlasnikService: () => new VlasnikService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stan = entity;
        stanServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stanServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stan = entity;
        stanServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stanServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
