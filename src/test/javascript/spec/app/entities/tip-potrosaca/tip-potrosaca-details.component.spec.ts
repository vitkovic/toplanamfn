/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TipPotrosacaDetailComponent from '@/entities/tip-potrosaca/tip-potrosaca-details.vue';
import TipPotrosacaClass from '@/entities/tip-potrosaca/tip-potrosaca-details.component';
import TipPotrosacaService from '@/entities/tip-potrosaca/tip-potrosaca.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TipPotrosaca Management Detail Component', () => {
    let wrapper: Wrapper<TipPotrosacaClass>;
    let comp: TipPotrosacaClass;
    let tipPotrosacaServiceStub: SinonStubbedInstance<TipPotrosacaService>;

    beforeEach(() => {
      tipPotrosacaServiceStub = sinon.createStubInstance<TipPotrosacaService>(TipPotrosacaService);

      wrapper = shallowMount<TipPotrosacaClass>(TipPotrosacaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { tipPotrosacaService: () => tipPotrosacaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipPotrosaca = { id: 123 };
        tipPotrosacaServiceStub.find.resolves(foundTipPotrosaca);

        // WHEN
        comp.retrieveTipPotrosaca(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipPotrosaca).toBe(foundTipPotrosaca);
      });
    });
  });
});
