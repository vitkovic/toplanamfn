import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Stan = () => import('@/entities/stan/stan.vue');
// prettier-ignore
const StanUpdate = () => import('@/entities/stan/stan-update.vue');
// prettier-ignore
const StanDetails = () => import('@/entities/stan/stan-details.vue');
// prettier-ignore
const Vlasnik = () => import('@/entities/vlasnik/vlasnik.vue');
// prettier-ignore
const VlasnikUpdate = () => import('@/entities/vlasnik/vlasnik-update.vue');
// prettier-ignore
const VlasnikDetails = () => import('@/entities/vlasnik/vlasnik-details.vue');
// prettier-ignore
const TipPotrosaca = () => import('@/entities/tip-potrosaca/tip-potrosaca.vue');
// prettier-ignore
const TipPotrosacaUpdate = () => import('@/entities/tip-potrosaca/tip-potrosaca-update.vue');
// prettier-ignore
const TipPotrosacaDetails = () => import('@/entities/tip-potrosaca/tip-potrosaca-details.vue');
// prettier-ignore
const Podstanica = () => import('@/entities/podstanica/podstanica.vue');
// prettier-ignore
const PodstanicaUpdate = () => import('@/entities/podstanica/podstanica-update.vue');
// prettier-ignore
const PodstanicaDetails = () => import('@/entities/podstanica/podstanica-details.vue');
// prettier-ignore
const StanjaPodstanice = () => import('@/entities/stanja-podstanice/stanja-podstanice.vue');
// prettier-ignore
const StanjaPodstaniceUpdate = () => import('@/entities/stanja-podstanice/stanja-podstanice-update.vue');
// prettier-ignore
const StanjaPodstaniceZbirnoKreiranje = () => import('@/entities/stanja-podstanice/stanja-podstanice-zbirno-kreiranje.vue');
// prettier-ignore
const StanjaPodstaniceDetails = () => import('@/entities/stanja-podstanice/stanja-podstanice-details.vue');
// prettier-ignore
const Transakcija = () => import('@/entities/transakcija/transakcija.vue');
const KnjigovodstveniDnevnik = () => import('@/entities/transakcija/knjigovodstveni-dnevnik.vue');
const KnjigovodstveniDnevnikAnaliticki = () => import('@/entities/transakcija/knjigovodstveni-dnevnik-analiticki.vue');
const RekapitulacijaSifraPromeneDatum = () => import('@/entities/transakcija/rekapitulacija-sifra-promene-datum.vue');
const SintetickiDnevnik = () => import('@/entities/transakcija/sinteticki-dnevnik.vue');
// prettier-ignore
const TransakcijaUpdate = () => import('@/entities/transakcija/transakcija-update.vue');
const TransakcijaZaduzenje = () => import('@/entities/transakcija/transakcija-zaduzenje.vue');
const TransakcijaRazduzenje = () => import('@/entities/transakcija/transakcija-razduzenje.vue');
const TransakcijaSveDetails = () => import('@/entities/transakcija/transakcija-sve-details.vue');

// prettier-ignore
const TransakcijaDetails = () => import('@/entities/transakcija/transakcija-details.vue');
// prettier-ignore
const Cene = () => import('@/entities/cene/cene.vue');
// prettier-ignore
const CeneUpdate = () => import('@/entities/cene/cene-update.vue');
// prettier-ignore
const CeneDetails = () => import('@/entities/cene/cene-details.vue');
// prettier-ignore
const VrstaTransakcije = () => import('@/entities/vrsta-transakcije/vrsta-transakcije.vue');
// prettier-ignore
const VrstaTransakcijeUpdate = () => import('@/entities/vrsta-transakcije/vrsta-transakcije-update.vue');
// prettier-ignore
const VrstaTransakcijeDetails = () => import('@/entities/vrsta-transakcije/vrsta-transakcije-details.vue');
// prettier-ignore
const SifraDokumenta = () => import('@/entities/sifra-dokumenta/sifra-dokumenta.vue');
// prettier-ignore
const SifraDokumentaUpdate = () => import('@/entities/sifra-dokumenta/sifra-dokumenta-update.vue');
// prettier-ignore
const SifraDokumentaDetails = () => import('@/entities/sifra-dokumenta/sifra-dokumenta-details.vue');
// prettier-ignore
const SifraPromene = () => import('@/entities/sifra-promene/sifra-promene.vue');
// prettier-ignore
const SifraPromeneUpdate = () => import('@/entities/sifra-promene/sifra-promene-update.vue');
// prettier-ignore
const SifraPromeneDetails = () => import('@/entities/sifra-promene/sifra-promene-details.vue');
// prettier-ignore
const NacrtRacuna = () => import('@/entities/nacrt-racuna/nacrt-racuna.vue');
const RekapitulacijaPdv = () => import('@/entities/nacrt-racuna/rekapitulacija-pdv.vue');
// prettier-ignore
const NacrtRacunaUpdate = () => import('@/entities/nacrt-racuna/nacrt-racuna-update.vue');

// prettier-ignore
const NacrtRacunaDetails = () => import('@/entities/nacrt-racuna/nacrt-racuna-details.vue');
// prettier-ignore
const Racun = () => import('@/entities/racun/racun.vue');
// prettier-ignore
const RacunUpdate = () => import('@/entities/racun/racun-update.vue');
// prettier-ignore
const RacunDetails = () => import('@/entities/racun/racun-details.vue');

const RacunBanka = () => import('@/entities/racun/racun-banka.vue');

// prettier-ignore
const Izvod = () => import('@/entities/izvod/izvod.vue');
// prettier-ignore
const IzvodUpdate = () => import('@/entities/izvod/izvod-update.vue');
// prettier-ignore
const IzvodDetails = () => import('@/entities/izvod/izvod-details.vue');

const IzvodPostanska = () => import('@/entities/izvod/izvod-postanska.vue');
// prettier-ignore
const IzvodPostanskaUpdate = () => import('@/entities/izvod/izvod-postanska-update.vue');
// prettier-ignore
const IzvodPostanskaDetails = () => import('@/entities/izvod/izvod-postanska-details.vue');

const NoveStaniceStanja = () => import('@/entities/stanja-podstanice/nove_stanice_stanja.vue');

// prettier-ignore
const StavkeIzvoda = () => import('@/entities/stavke-izvoda/stavke-izvoda.vue');
// prettier-ignore
const StavkeIzvodaUpdate = () => import('@/entities/stavke-izvoda/stavke-izvoda-update.vue');
// prettier-ignore
const StavkeIzvodaDetails = () => import('@/entities/stavke-izvoda/stavke-izvoda-details.vue');

const StavkeIzvodaPostanska = () => import('@/entities/stavke-izvoda-postanska/stavke-izvoda-postanska.vue');
// prettier-ignore
const StavkeIzvodaPostanskaUpdate = () => import('@/entities/stavke-izvoda-postanska/stavke-izvoda-postanska-update.vue');
// prettier-ignore
const StavkeIzvodaPostanskaDetails = () => import('@/entities/stavke-izvoda-postanska/stavke-izvoda-postanska-details.vue');

// prettier-ignore
const TransakcijaStara = () => import('@/entities/transakcija-stara/transakcija-stara.vue');
// prettier-ignore
const TransakcijaStaraUpdate = () => import('@/entities/transakcija-stara/transakcija-stara-update.vue');
// prettier-ignore
const TransakcijaStaraDetails = () => import('@/entities/transakcija-stara/transakcija-stara-details.vue');
// prettier-ignore
const CeneStare = () => import('@/entities/cene-stare/cene-stare.vue');
// prettier-ignore
const CeneStareUpdate = () => import('@/entities/cene-stare/cene-stare-update.vue');
// prettier-ignore
const CeneStareDetails = () => import('@/entities/cene-stare/cene-stare-details.vue');
// prettier-ignore
const Parametri = () => import('@/entities/parametri/parametri.vue');
// prettier-ignore
const ParametriUpdate = () => import('@/entities/parametri/parametri-update.vue');
// prettier-ignore
const ParametriDetails = () => import('@/entities/parametri/parametri-details.vue');
// prettier-ignore
const ParametriIstorija = () => import('@/entities/parametri-istorija/parametri-istorija.vue');
// prettier-ignore
const ParametriIstorijaUpdate = () => import('@/entities/parametri-istorija/parametri-istorija-update.vue');
// prettier-ignore
const ParametriIstorijaDetails = () => import('@/entities/parametri-istorija/parametri-istorija-details.vue');
// prettier-ignore
const Opomena = () => import('@/entities/opomena/opomena.vue');
// prettier-ignore
const OpomenaUpdate = () => import('@/entities/opomena/opomena-update.vue');
// prettier-ignore
const OpomenaDetails = () => import('@/entities/opomena/opomena-details.vue');

const OstaliRacuni = () => import('@/entities/ostali-racuni/ostali-racuni.vue');
const OstaliRacuniUpdate = () => import('@/entities/ostali-racuni/ostali-racuni-update.vue');
const OstaliRacuniDetails = () => import('@/entities/ostali-racuni/ostali-racuni-details.vue');

// prettier-ignore
const Utuzenje = () => import('@/entities/utuzenje/utuzenje.vue');
// prettier-ignore
const UtuzenjeUpdate = () => import('@/entities/utuzenje/utuzenje-update.vue');
// prettier-ignore
const UtuzenjeDetails = () => import('@/entities/utuzenje/utuzenje-details.vue');
// prettier-ignore
const StavkeUtuzenja = () => import('@/entities/stavke-utuzenja/stavke-utuzenja.vue');
// prettier-ignore
const StavkeUtuzenjaUpdate = () => import('@/entities/stavke-utuzenja/stavke-utuzenja-update.vue');
// prettier-ignore
const StavkeUtuzenjaDetails = () => import('@/entities/stavke-utuzenja/stavke-utuzenja-details.vue');
// prettier-ignore
const UgovorRate = () => import('@/entities/ugovor-rate/ugovor-rate.vue');
// prettier-ignore
const UgovorRateUpdate = () => import('@/entities/ugovor-rate/ugovor-rate-update.vue');
// prettier-ignore
const UgovorRateDetails = () => import('@/entities/ugovor-rate/ugovor-rate-details.vue');
// prettier-ignore
const StanjaPodstaniceZaRacun = () => import('@/entities/stanja-podstanice-za-racun/stanja-podstanice-za-racun.vue');
// prettier-ignore
const StanjaPodstaniceZaRacunUpdate = () => import('@/entities/stanja-podstanice-za-racun/stanja-podstanice-za-racun-update.vue');
// prettier-ignore
const StanjaPodstaniceZaRacunDetails = () => import('@/entities/stanja-podstanice-za-racun/stanja-podstanice-za-racun-details.vue');
// prettier-ignore
const StanStanje = () => import('@/entities/stan-stanje/stan-stanje.vue');
// prettier-ignore
const StanStanjeUpdate = () => import('@/entities/stan-stanje/stan-stanje-update.vue');
// prettier-ignore
const StanStanjeDetails = () => import('@/entities/stan-stanje/stan-stanje-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/stan',
    name: 'Stan',
    component: Stan,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan/new',
    name: 'StanCreate',
    component: StanUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan/:stanId/edit',
    name: 'StanEdit',
    component: StanUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan/:stanId/view',
    name: 'StanView',
    component: StanDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik',
    name: 'Vlasnik',
    component: Vlasnik,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/new',
    name: 'VlasnikCreate',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/edit',
    name: 'VlasnikEdit',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/view',
    name: 'VlasnikView',
    component: VlasnikDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tip-potrosaca',
    name: 'TipPotrosaca',
    component: TipPotrosaca,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tip-potrosaca/new',
    name: 'TipPotrosacaCreate',
    component: TipPotrosacaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tip-potrosaca/:tipPotrosacaId/edit',
    name: 'TipPotrosacaEdit',
    component: TipPotrosacaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tip-potrosaca/:tipPotrosacaId/view',
    name: 'TipPotrosacaView',
    component: TipPotrosacaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/podstanica',
    name: 'Podstanica',
    component: Podstanica,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/podstanica/new',
    name: 'PodstanicaCreate',
    component: PodstanicaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/podstanica/:podstanicaId/edit',
    name: 'PodstanicaEdit',
    component: PodstanicaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/podstanica/:podstanicaId/view',
    name: 'PodstanicaView',
    component: PodstanicaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice',
    name: 'StanjaPodstanice',
    component: StanjaPodstanice,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice/new',
    name: 'StanjaPodstaniceCreate',
    component: StanjaPodstaniceZbirnoKreiranje,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice/:stanjaPodstaniceId/edit',
    name: 'StanjaPodstaniceEdit',
    component: StanjaPodstaniceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice/:stanjaPodstaniceId/view',
    name: 'StanjaPodstaniceView',
    component: StanjaPodstaniceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/knjigovodstveni-dnevnik',
    name: 'KnjigovodstveniDnevnik',
    component: KnjigovodstveniDnevnik,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/knjigovodstveni-dnevnik-analiticki',
    name: 'KnjigovodstveniDnevnikAnaliticki',
    component: KnjigovodstveniDnevnikAnaliticki,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rekapitulacija-sifra-promene-datum',
    name: 'RekapitulacijaSifraPromeneDatum',
    component: RekapitulacijaSifraPromeneDatum,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sinteticki-dnevnik',
    name: 'SintetickiDnevnik',
    component: SintetickiDnevnik,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/transakcija',
    name: 'Transakcija',
    component: Transakcija,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija/new',
    name: 'TransakcijaCreate',
    component: TransakcijaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija-zaduzenje',
    name: 'TransakcijaZaduzenje',
    component: TransakcijaZaduzenje,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija-razduzenje',
    name: 'TransakcijaRazduzenje',
    component: TransakcijaRazduzenje,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija/sve-prikaz/:sifra',
    name: 'TransakcijaSveDetails',
    component: TransakcijaSveDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija/:transakcijaId/edit',
    name: 'TransakcijaEdit',
    component: TransakcijaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija/:transakcijaId/view',
    name: 'TransakcijaView',
    component: TransakcijaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene',
    name: 'Cene',
    component: Cene,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene/new',
    name: 'CeneCreate',
    component: CeneUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene/:ceneId/edit',
    name: 'CeneEdit',
    component: CeneUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene/:ceneId/view',
    name: 'CeneView',
    component: CeneDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vrsta-transakcije',
    name: 'VrstaTransakcije',
    component: VrstaTransakcije,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vrsta-transakcije/new',
    name: 'VrstaTransakcijeCreate',
    component: VrstaTransakcijeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vrsta-transakcije/:vrstaTransakcijeId/edit',
    name: 'VrstaTransakcijeEdit',
    component: VrstaTransakcijeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vrsta-transakcije/:vrstaTransakcijeId/view',
    name: 'VrstaTransakcijeView',
    component: VrstaTransakcijeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-dokumenta',
    name: 'SifraDokumenta',
    component: SifraDokumenta,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-dokumenta/new',
    name: 'SifraDokumentaCreate',
    component: SifraDokumentaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-dokumenta/:sifraDokumentaId/edit',
    name: 'SifraDokumentaEdit',
    component: SifraDokumentaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-dokumenta/:sifraDokumentaId/view',
    name: 'SifraDokumentaView',
    component: SifraDokumentaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-promene',
    name: 'SifraPromene',
    component: SifraPromene,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-promene/new',
    name: 'SifraPromeneCreate',
    component: SifraPromeneUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-promene/:sifraPromeneId/edit',
    name: 'SifraPromeneEdit',
    component: SifraPromeneUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-promene/:sifraPromeneId/view',
    name: 'SifraPromeneView',
    component: SifraPromeneDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/nacrt-racuna',
    name: 'NacrtRacuna',
    component: NacrtRacuna,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/nacrt-racuna/new',
    name: 'NacrtRacunaCreate',
    component: NacrtRacunaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/nacrt-racuna/:nacrtRacunaId/edit',
    name: 'NacrtRacunaEdit',
    component: NacrtRacunaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/nacrt-racuna/:nacrtRacunaId/view',
    name: 'NacrtRacunaView',
    component: NacrtRacunaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rekapitulacija-pdv/:nacrtRacunaId',
    name: 'RekapitulacijaPdv',
    component: RekapitulacijaPdv,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sifra-promene/:sifraPromeneId/view',
    name: 'SifraPromeneView',
    component: SifraPromeneDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/racun',
    name: 'Racun',
    component: Racun,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/racun/new',
    name: 'RacunCreate',
    component: RacunUpdate,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/racun/:racunId/edit',
    name: 'RacunEdit',
    component: RacunUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/racun/:racunId/view',
    name: 'RacunView',
    component: RacunDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/racun-banka',
    name: 'RacunBanka',
    component: RacunBanka,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/izvod',
    name: 'Izvod',
    component: Izvod,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod/new',
    name: 'IzvodCreate',
    component: IzvodUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanjenovestanice/',
    name: 'StanStanjeImport',
    component: NoveStaniceStanja,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod/:izvodId/edit',
    name: 'IzvodEdit',
    component: IzvodUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod/:izvodId/view',
    name: 'IzvodView',
    component: IzvodDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/izvod-postanska',
    name: 'IzvodPostanska',
    component: IzvodPostanska,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod-postanska/new',
    name: 'IzvodPostanskaCreate',
    component: IzvodPostanskaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod-postanska/:izvodId/edit',
    name: 'IzvodPostanskaEdit',
    component: IzvodPostanskaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/izvod-postanska/:izvodId/view',
    name: 'IzvodPostanskaView',
    component: IzvodPostanskaDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/stavke-izvoda',
    name: 'StavkeIzvoda',
    component: StavkeIzvoda,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda/new',
    name: 'StavkeIzvodaCreate',
    component: StavkeIzvodaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda/:stavkeIzvodaId/edit',
    name: 'StavkeIzvodaEdit',
    component: StavkeIzvodaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda/:stavkeIzvodaId/view',
    name: 'StavkeIzvodaView',
    component: StavkeIzvodaDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/stavke-izvoda-postanska',
    name: 'StavkeIzvodaPostanska',
    component: StavkeIzvodaPostanska,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda-postanska/new',
    name: 'StavkeIzvodaPostanskaCreate',
    component: StavkeIzvodaPostanskaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda-postanska/:stavkeIzvodaId/edit',
    name: 'StavkeIzvodaPostanskaEdit',
    component: StavkeIzvodaPostanskaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-izvoda-postanska/:stavkeIzvodaId/view',
    name: 'StavkeIzvodaPostanskaView',
    component: StavkeIzvodaPostanskaDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/transakcija-stara',
    name: 'TransakcijaStara',
    component: TransakcijaStara,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija-stara/new',
    name: 'TransakcijaStaraCreate',
    component: TransakcijaStaraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija-stara/:transakcijaStaraId/edit',
    name: 'TransakcijaStaraEdit',
    component: TransakcijaStaraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transakcija-stara/:transakcijaStaraId/view',
    name: 'TransakcijaStaraView',
    component: TransakcijaStaraDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene-stare',
    name: 'CeneStare',
    component: CeneStare,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene-stare/new',
    name: 'CeneStareCreate',
    component: CeneStareUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene-stare/:ceneStareId/edit',
    name: 'CeneStareEdit',
    component: CeneStareUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cene-stare/:ceneStareId/view',
    name: 'CeneStareView',
    component: CeneStareDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri',
    name: 'Parametri',
    component: Parametri,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri/new',
    name: 'ParametriCreate',
    component: ParametriUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri/:parametriId/edit',
    name: 'ParametriEdit',
    component: ParametriUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri/:parametriId/view',
    name: 'ParametriView',
    component: ParametriDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri-istorija',
    name: 'ParametriIstorija',
    component: ParametriIstorija,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri-istorija/new',
    name: 'ParametriIstorijaCreate',
    component: ParametriIstorijaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri-istorija/:parametriIstorijaId/edit',
    name: 'ParametriIstorijaEdit',
    component: ParametriIstorijaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/parametri-istorija/:parametriIstorijaId/view',
    name: 'ParametriIstorijaView',
    component: ParametriIstorijaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/opomena',
    name: 'Opomena',
    component: Opomena,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/opomena/new',
    name: 'OpomenaCreate',
    component: OpomenaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/opomena/:opomenaId/edit',
    name: 'OpomenaEdit',
    component: OpomenaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/opomena/:opomenaId/view',
    name: 'OpomenaView',
    component: OpomenaDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/ostali-racuni',
    name: 'OstaliRacuni',
    component: OstaliRacuni,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ostali-racuni/new',
    name: 'OstaliRacuniCreate',
    component: OstaliRacuniUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ostali-racuni/:ostaliRacuniId/edit',
    name: 'OstaliRacuniEdit',
    component: OstaliRacuniUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ostali-racuni/:ostalIRacuniId/view',
    name: 'OstaliRacuniView',
    component: OstaliRacuniDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/utuzenje',
    name: 'Utuzenje',
    component: Utuzenje,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/utuzenje/new',
    name: 'UtuzenjeCreate',
    component: UtuzenjeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/utuzenje/:utuzenjeId/edit',
    name: 'UtuzenjeEdit',
    component: UtuzenjeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/utuzenje/:utuzenjeId/view',
    name: 'UtuzenjeView',
    component: UtuzenjeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-utuzenja',
    name: 'StavkeUtuzenja',
    component: StavkeUtuzenja,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-utuzenja/new',
    name: 'StavkeUtuzenjaCreate',
    component: StavkeUtuzenjaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-utuzenja/:stavkeUtuzenjaId/edit',
    name: 'StavkeUtuzenjaEdit',
    component: StavkeUtuzenjaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stavke-utuzenja/:stavkeUtuzenjaId/view',
    name: 'StavkeUtuzenjaView',
    component: StavkeUtuzenjaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ugovor-rate',
    name: 'UgovorRate',
    component: UgovorRate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ugovor-rate/new',
    name: 'UgovorRateCreate',
    component: UgovorRateUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ugovor-rate/:ugovorRateId/edit',
    name: 'UgovorRateEdit',
    component: UgovorRateUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ugovor-rate/:ugovorRateId/view',
    name: 'UgovorRateView',
    component: UgovorRateDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/stanja-podstanice-za-racun',
    name: 'StanjaPodstaniceZaRacun',
    component: StanjaPodstaniceZaRacun,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice-za-racun/new',
    name: 'StanjaPodstaniceZaRacunCreate',
    component: StanjaPodstaniceZaRacunUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice-za-racun/:stanjaPodstaniceZaRacunId/edit',
    name: 'StanjaPodstaniceZaRacunEdit',
    component: StanjaPodstaniceZaRacunUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stanja-podstanice-za-racun/:stanjaPodstaniceZaRacunId/view',
    name: 'StanjaPodstaniceZaRacunView',
    component: StanjaPodstaniceZaRacunDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/stan-stanje',
    name: 'StanStanje',
    component: StanStanje,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan-stanje/new',
    name: 'StanStanjeCreate',
    component: StanStanjeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan-stanje/:stanStanjeId/edit',
    name: 'StanStanjeEdit',
    component: StanStanjeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/stan-stanje/:stanStanjeId/view',
    name: 'StanStanjeView',
    component: StanStanjeDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/vlasnik',
    name: 'Vlasnik',
    component: Vlasnik,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/new',
    name: 'VlasnikCreate',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/edit',
    name: 'VlasnikEdit',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/view',
    name: 'VlasnikView',
    component: VlasnikDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/vlasnik',
    name: 'Vlasnik',
    component: Vlasnik,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/new',
    name: 'VlasnikCreate',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/edit',
    name: 'VlasnikEdit',
    component: VlasnikUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/vlasnik/:vlasnikId/view',
    name: 'VlasnikView',
    component: VlasnikDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
