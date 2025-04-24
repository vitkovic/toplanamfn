/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StanStanjeDetailComponent from '@/entities/stan-stanje/stan-stanje-details.vue';
import StanStanjeClass from '@/entities/stan-stanje/stan-stanje-details.component';
import StanStanjeService from '@/entities/stan-stanje/stan-stanje.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StanStanje Management Detail Component', () => {
    let wrapper: Wrapper<StanStanjeClass>;
    let comp: StanStanjeClass;
    let stanStanjeServiceStub: SinonStubbedInstance<StanStanjeService>;

    beforeEach(() => {
      stanStanjeServiceStub = sinon.createStubInstance<StanStanjeService>(StanStanjeService);

      wrapper = shallowMount<StanStanjeClass>(StanStanjeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stanStanjeService: () => stanStanjeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStanStanje = { id: 123 };
        stanStanjeServiceStub.find.resolves(foundStanStanje);

        // WHEN
        comp.retrieveStanStanje(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stanStanje).toBe(foundStanStanje);
      });
    });
  });
});
