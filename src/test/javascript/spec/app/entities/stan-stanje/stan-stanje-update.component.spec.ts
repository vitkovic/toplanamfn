/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StanStanjeUpdateComponent from '@/entities/stan-stanje/stan-stanje-update.vue';
import StanStanjeClass from '@/entities/stan-stanje/stan-stanje-update.component';
import StanStanjeService from '@/entities/stan-stanje/stan-stanje.service';

import StanService from '@/entities/stan/stan.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('StanStanje Management Update Component', () => {
    let wrapper: Wrapper<StanStanjeClass>;
    let comp: StanStanjeClass;
    let stanStanjeServiceStub: SinonStubbedInstance<StanStanjeService>;

    beforeEach(() => {
      stanStanjeServiceStub = sinon.createStubInstance<StanStanjeService>(StanStanjeService);

      wrapper = shallowMount<StanStanjeClass>(StanStanjeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stanStanjeService: () => stanStanjeServiceStub,

          stanService: () => new StanService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.stanStanje = entity;
        stanStanjeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stanStanjeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stanStanje = entity;
        stanStanjeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stanStanjeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
