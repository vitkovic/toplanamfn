/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TipPotrosacaComponent from '@/entities/tip-potrosaca/tip-potrosaca.vue';
import TipPotrosacaClass from '@/entities/tip-potrosaca/tip-potrosaca.component';
import TipPotrosacaService from '@/entities/tip-potrosaca/tip-potrosaca.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('TipPotrosaca Management Component', () => {
    let wrapper: Wrapper<TipPotrosacaClass>;
    let comp: TipPotrosacaClass;
    let tipPotrosacaServiceStub: SinonStubbedInstance<TipPotrosacaService>;

    beforeEach(() => {
      tipPotrosacaServiceStub = sinon.createStubInstance<TipPotrosacaService>(TipPotrosacaService);
      tipPotrosacaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TipPotrosacaClass>(TipPotrosacaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          tipPotrosacaService: () => tipPotrosacaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tipPotrosacaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTipPotrosacas();
      await comp.$nextTick();

      // THEN
      expect(tipPotrosacaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipPotrosacas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      tipPotrosacaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTipPotrosaca();
      await comp.$nextTick();

      // THEN
      expect(tipPotrosacaServiceStub.delete.called).toBeTruthy();
      expect(tipPotrosacaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
