/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StanDetailComponent from '@/entities/stan/stan-details.vue';
import StanClass from '@/entities/stan/stan-details.component';
import StanService from '@/entities/stan/stan.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Stan Management Detail Component', () => {
    let wrapper: Wrapper<StanClass>;
    let comp: StanClass;
    let stanServiceStub: SinonStubbedInstance<StanService>;

    beforeEach(() => {
      stanServiceStub = sinon.createStubInstance<StanService>(StanService);

      wrapper = shallowMount<StanClass>(StanDetailComponent, { store, i18n, localVue, provide: { stanService: () => stanServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStan = { id: 123 };
        stanServiceStub.find.resolves(foundStan);

        // WHEN
        comp.retrieveStan(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stan).toBe(foundStan);
      });
    });
  });
});
