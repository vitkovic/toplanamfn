/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import VlasnikDetailComponent from '@/entities/vlasnik/vlasnik-details.vue';
import VlasnikClass from '@/entities/vlasnik/vlasnik-details.component';
import VlasnikService from '@/entities/vlasnik/vlasnik.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Vlasnik Management Detail Component', () => {
    let wrapper: Wrapper<VlasnikClass>;
    let comp: VlasnikClass;
    let vlasnikServiceStub: SinonStubbedInstance<VlasnikService>;

    beforeEach(() => {
      vlasnikServiceStub = sinon.createStubInstance<VlasnikService>(VlasnikService);

      wrapper = shallowMount<VlasnikClass>(VlasnikDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { vlasnikService: () => vlasnikServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVlasnik = { id: 123 };
        vlasnikServiceStub.find.resolves(foundVlasnik);

        // WHEN
        comp.retrieveVlasnik(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vlasnik).toBe(foundVlasnik);
      });
    });
  });
});
