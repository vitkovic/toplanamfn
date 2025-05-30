/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import VlasnikUpdateComponent from '@/entities/vlasnik/vlasnik-update.vue';
import VlasnikClass from '@/entities/vlasnik/vlasnik-update.component';
import VlasnikService from '@/entities/vlasnik/vlasnik.service';

import StanService from '@/entities/stan/stan.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Vlasnik Management Update Component', () => {
    let wrapper: Wrapper<VlasnikClass>;
    let comp: VlasnikClass;
    let vlasnikServiceStub: SinonStubbedInstance<VlasnikService>;

    beforeEach(() => {
      vlasnikServiceStub = sinon.createStubInstance<VlasnikService>(VlasnikService);

      wrapper = shallowMount<VlasnikClass>(VlasnikUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          vlasnikService: () => vlasnikServiceStub,

          stanService: () => new StanService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.vlasnik = entity;
        vlasnikServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vlasnikServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vlasnik = entity;
        vlasnikServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vlasnikServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
