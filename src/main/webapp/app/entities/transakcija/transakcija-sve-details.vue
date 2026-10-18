<template>
  <div class="container-fluid analiticka-kartica">
    <div class="row justify-content-center">
      <div class="col-12">

        <div v-if="transakcijaZbirno">

          <!-- NASLOV -->
          <div class="row">
            <div class="col-12">
              <h2 class="jh-entity-heading mb-3">
                <span
                  v-text="$t('toplanaApp.transakcija.analitickaKartica')"
                ></span>
              </h2>
            </div>
          </div>

          <!-- PODACI O KORISNIKU -->
          <div
            v-if="transakcijaZbirno.stan || transakcijaZbirno.ostaliRacuni"
            class="customer-panel"
          >
            <div class="row">

              <div class="col-12 col-sm-6 col-lg-3 customer-item">
                <span class="customer-label">
                  <span
                    v-text="$t('toplanaApp.transakcija.maticniBroj')"
                  ></span>:
                </span>

                <span v-if="transakcijaZbirno.stan">
                  {{ transakcijaZbirno.stan.sifra }}
                </span>

                <span v-if="transakcijaZbirno.ostaliRacuni">
                  {{ transakcijaZbirno.ostaliRacuni.sifra }}
                </span>
              </div>

              <div class="col-12 col-sm-6 col-lg-3 customer-item">
                <span class="customer-label">Корисник:</span>

                <span v-if="transakcijaZbirno.stan">
                  {{ transakcijaZbirno.stan.vlasnik.prezime }}
                  {{ transakcijaZbirno.stan.vlasnik.ime }}
                </span>

                <span v-if="transakcijaZbirno.ostaliRacuni">
                  {{ transakcijaZbirno.ostaliRacuni.naziv }}
                </span>
              </div>

              <div class="col-12 col-sm-6 col-lg-2 customer-item">
                <span class="customer-label">Град:</span>

                <span v-if="transakcijaZbirno.stan">
                  {{ transakcijaZbirno.stan.grad }}
                </span>

                <span
                  v-if="
                    transakcijaZbirno.ostaliRacuni &&
                    transakcijaZbirno.ostaliRacuni.stan
                  "
                >
                  {{ transakcijaZbirno.ostaliRacuni.stan.grad }}
                </span>
              </div>

              <div class="col-12 col-sm-6 col-lg-4 customer-item">
                <span class="customer-label">Адреса:</span>

                <span v-if="transakcijaZbirno.stan">
                  {{ transakcijaZbirno.stan.ulica }}
                  {{ transakcijaZbirno.stan.ulaz }}/{{ transakcijaZbirno.stan.broj }}
                </span>

                <span
                  v-if="
                    transakcijaZbirno.ostaliRacuni &&
                    transakcijaZbirno.ostaliRacuni.stan
                  "
                >
                  {{ transakcijaZbirno.ostaliRacuni.stan.ulica }}
                  {{ transakcijaZbirno.ostaliRacuni.stan.ulaz }}/
                  {{ transakcijaZbirno.ostaliRacuni.stan.broj }}
                </span>
              </div>

            </div>
          </div>

          <!-- FILTERI -->
          <div class="filter-panel">

            <div class="filter-checkbox">
              <input
                id="sveGodine"
                type="checkbox"
                v-model="sve"
                @change="onSveChange"
              />
              <label for="sveGodine">Све године (Брише филтере)</label>
            </div>

            <div class="filter-checkbox">
              <input
                id="racuni"
                type="checkbox"
                v-model="racuni"
                @change="onEnterOpis"
              />
              <label for="racuni">Рачуни</label>
            </div>

            <div class="filter-checkbox">
              <input
                id="uplate"
                type="checkbox"
                v-model="uplate"
                @change="onEnterOpis"
              />
              <label for="uplate">Уплате</label>
            </div>

            <div class="filter-description">
              <input
                type="text"
                class="form-control"
                v-model="opis"
                placeholder="Опис"
                @keyup.enter="onEnterOpis"
              />
            </div>

            <button
              type="button"
              class="btn btn-primary filter-button"
              @click="onEnterOpis"
            >
              Претражи
            </button>

          </div>


          <div class="open-items-panel">

            <div class="open-items-description">
              <strong>Отворене ставке</strong>
              <span>Приказ неповезаних рачуна и уплата</span>
            </div>

            <button
              type="button"
              class="btn btn-warning open-items-button"
              @click="prikaziOtvoreneStavke"
            >
              Отворене ставке
            </button>

          </div>

          <hr class="new3" />

          <!-- TABELA -->
          <div
            v-if="
              transakcijaZbirno.transakcije &&
              (prikazOtvorenihStavki
                ? transakcijaZbirno.transakcije.some(t => Number(t.duguje) > 0)
                : transakcijaZbirno.transakcije.length > 0)
            "
            class="table-responsive transaction-table-wrapper"
          >
            <table class="table table-striped table-hover transaction-table">

              <thead>
                <tr>
                  <th class="sticky">
                    <span
                      v-text="$t('toplanaApp.transakcija.datumKnjizenja')"
                    ></span>
                  </th>

                  <th class="sticky">
                    <span
                      v-text="$t('toplanaApp.transakcija.sifraDokumenta')"
                    ></span>
                  </th>

                  <th class="sticky">
                    <span
                      v-text="$t('toplanaApp.transakcija.sifraPromene')"
                    ></span>
                  </th>

                  <th class="sticky description-column">
                    <span
                      v-text="$t('toplanaApp.transakcija.opis')"
                    ></span>
                  </th>

                  <th class="sticky description-column">
                    <span
                      v-text="$t('toplanaApp.racun.opisRacuna')"
                    ></span>
                  </th>

                  <th class="sticky numeric-column">
                    <span
                      v-text="$t('toplanaApp.transakcija.duguje')"
                    ></span>
                  </th>

                  <th class="sticky numeric-column">
                    <span
                      v-text="$t('toplanaApp.transakcija.potrazuje')"
                    ></span>
                  </th>

                  <th class="sticky numeric-column">
                    <span
                      v-text="$t('toplanaApp.transakcija.saldo')"
                    ></span>
                  </th>

                  <th class="sticky action-column">
                    <span
                      v-text="$t('toplanaApp.transakcija.racunIliIzvod')"
                    ></span>
                  </th>
                </tr>
              </thead>

              <tbody>

				<tr
				    v-for="(t, index) in transakcijaZbirno.transakcije"
				    v-if="!prikazOtvorenihStavki || Number(t.duguje) > 0"
				    :key="t.id"
				    :class="{
				        'ivk-racun-row': prikazOtvorenihStavki && t.opisRacuna && t.opisRacuna.toLowerCase().includes('ivk'),
				        'ivk-uplata-row': prikazOtvorenihStavki && t.opis && t.opis.toLowerCase().includes('ivk')
				    }"
                >
                  <td class="date-column">
                    {{
                      t.datumKnjizenja
                        ? $d(Date.parse(t.datumKnjizenja), 'short')
                        : ''
                    }}
                  </td>

                  <td>
                    {{ t.sifraDokumenta }}
                  </td>

                  <td>
                    {{ t.sifraPromene }}
                  </td>

                  <td class="description-cell">
                    {{ t.opis }}
                  </td>

                  <td class="description-cell">
                    {{ t.opisRacuna }}
                  </td>

                  <td class="numeric-cell">
                    {{ t.duguje == 0 ? '' : t.duguje | currency('') }}
                  </td>

                  <td class="numeric-cell">
                    {{ t.potrazuje == 0 ? '' : t.potrazuje | currency('') }}
                  </td>

                  <td class="numeric-cell">
                    {{ (prikazOtvorenihStavki ? t.duguje : t.saldo) | currency('') }}
                  </td>

                  <td class="action-cell">
                    <details v-if="t.originalniIznos != null" class="open-item-audit">
                      <summary>Расподела</summary>
                      <div><strong>Оригинални износ:</strong> {{ t.originalniIznos | currency('') }}</div>
                      <div><strong>Распоређено:</strong> {{ t.rasporedjenoUkupno | currency('') }}</div>
                      <div><strong>Преостало:</strong> {{ (t.duguje || t.potrazuje) | currency('') }}</div>
                      <table v-if="t.raspodelaUplata && t.raspodelaUplata.length" class="table table-sm">
                        <thead><tr><th>Датум уплате</th><th>Документ</th><th>Опис</th><th>Распоређено</th></tr></thead>
                        <tbody>
                          <tr v-for="(u, i) in t.raspodelaUplata" :key="i">
                            <td>{{ u.datum }}</td><td>{{ u.sifraDokumenta }}</td>
                            <td>{{ u.opis }}</td><td>{{ u.iznos | currency('') }}</td>
                          </tr>
                        </tbody>
                      </table>
                    </details>
                    <button
                      v-if="
                        t.racunId ||
                        t.stavkaIzvodaId ||
                        t.stavkaIzvodaPostanskaId
                      "
                      type="button"
                      class="btn btn-info btn-sm"
                      @click="pogledaj(index)"
                    >
                      <span
                        v-text="$t('toplanaApp.transakcija.pogledaj')"
                      ></span>
                    </button>
                  </td>
                </tr>

                <!-- UKUPNO -->
                <tr class="total-row">
                  <td colspan="5" class="total-label">
                    <strong>
                      <span
                        v-text="$t('toplanaApp.transakcija.ukupno')"
                      ></span>:
                      <span v-if="prikazOtvorenihStavki"> (отворена задужења)</span>
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ (prikazOtvorenihStavki ? transakcijaZbirno.transakcije.reduce((sum, t) => sum + Math.max(0, Number(t.duguje) || 0), 0) : transakcijaZbirno.dugujeUkupno) | currency('') }}
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ (prikazOtvorenihStavki ? 0 : transakcijaZbirno.potrazujeUkupno) | currency('') }}
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ (prikazOtvorenihStavki ? transakcijaZbirno.transakcije.reduce((sum, t) => sum + Math.max(0, Number(t.duguje) || 0), 0) : transakcijaZbirno.saldoUkupno) | currency('') }}
                    </strong>
                  </td>

                  <td></td>
                </tr>

              </tbody>
            </table>
          </div>

          <div
            v-else
            class="alert alert-info"
          >
            {{ prikazOtvorenihStavki ? 'Нема преосталих отворених ставки.' : 'Нема трансакција за изабрани критеријум.' }}
          </div>

          <!-- Pregled uplata i korekcija: samo prikaz, bez izmene obracuna -->
          <details
            v-if="prikazOtvorenihStavki && transakcijaZbirno.pregledUplata && transakcijaZbirno.pregledUplata.length"
            class="mb-3 payment-review"
            open
          >
            <summary><strong>Уплате и корекције — где су износи распоређени</strong></summary>
            <div class="payment-review-total mt-2">
              <span>Књиговодствени салдо</span>
              <strong>{{ transakcijaZbirno.saldoUkupno | currency('') }} RSD</strong>
            </div>
            <p class="payment-review-hint">За сваку уплату или корекцију кликните на „Расподела“ да видите која су задужења умањена.</p>
            <div class="table-responsive">
              <table class="table table-sm table-bordered payment-review-table">
                <thead>
                  <tr>
                    <th><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('datum')">Датум <span v-if="otvoreneUplateSortKolona === 'datum'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('sifraDokumenta')">Документ <span v-if="otvoreneUplateSortKolona === 'sifraDokumenta'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('opis')">Опис <span v-if="otvoreneUplateSortKolona === 'opis'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('vrsta')">Врста <span v-if="otvoreneUplateSortKolona === 'vrsta'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th class="numeric-cell"><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('originalniIznos')">Износ <span v-if="otvoreneUplateSortKolona === 'originalniIznos'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th class="numeric-cell"><button type="button" class="open-items-sort" @click="sortirajOtvoreneUplate('nerasporedjeno')">Нераспоређено <span v-if="otvoreneUplateSortKolona === 'nerasporedjeno'">{{ otvoreneUplateSortSmer === 1 ? '▲' : '▼' }}</span></button></th>
                    <th>Детаљи</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="u in sortiraneOtvoreneUplate" :key="u.transakcijaId" :class="{ 'ivk-uplata-row': u.opis && /ivk/i.test(u.opis) }">
                    <td>{{ u.datum }}</td>
                    <td>{{ u.sifraDokumenta }}</td>
                    <td>{{ u.opis }}</td>
                    <td>{{ u.vrsta === 'KOREKCIJA' ? 'Корекција / отпис' : 'Уплата' }}</td>
                    <td class="numeric-cell">{{ u.originalniIznos | currency('') }}</td>
                    <td class="numeric-cell">{{ u.nerasporedjeno | currency('') }}</td>
                    <td class="payment-review-detail-cell">
                      <details>
                        <summary>Расподела</summary>
                        <div class="payment-review-detail-content">
                          <table class="table table-sm mb-1">
                            <thead>
                              <tr>
                                <th>Задужење које је умањено</th>
                                <th class="numeric-cell">Износ</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr v-for="(r, i) in u.raspodele" :key="i">
                                <td>{{ r.racun === 'ZR 22' ? 'Почетни дуг (ZR 22)' : (r.racun || 'Задужење') }}<span v-if="r.datumRacuna"> ({{ r.datumRacuna }})</span></td>
                                <td class="numeric-cell">{{ r.iznos | currency('') }}</td>
                              </tr>
                              <tr v-if="!u.raspodele || !u.raspodele.length">
                                <td colspan="2">Нема распоређених износа.</td>
                              </tr>
                            </tbody>
                            <tfoot>
                              <tr>
                                <th>Укупно распоређено</th>
                                <th class="numeric-cell">{{ u.rasporedjeno | currency('') }}</th>
                              </tr>
                            </tfoot>
                          </table>
                          <div class="payment-review-remainder">Нераспоређено: <strong>{{ u.nerasporedjeno | currency('') }}</strong></div>
                        </div>
                      </details>
                    </td>
                  </tr>
                </tbody>
                <tfoot>
                  <tr>
                    <th colspan="4">Укупно:</th>
                    <th class="numeric-cell">{{ transakcijaZbirno.pregledUplata.reduce((zbir, u) => zbir + Number(u.originalniIznos || 0), 0) | currency('') }}</th>
                    <th class="numeric-cell">{{ transakcijaZbirno.pregledUplata.reduce((zbir, u) => zbir + Number(u.nerasporedjeno || 0), 0) | currency('') }}</th>
                    <th></th>
                  </tr>
                  <tr>
                    <th colspan="4">Укупно распоређено:</th>
                    <th class="numeric-cell">{{ transakcijaZbirno.pregledUplata.reduce((zbir, u) => zbir + Number(u.rasporedjeno || 0), 0) | currency('') }}</th>
                    <th colspan="2"></th>
                  </tr>
                </tfoot>
              </table>
            </div>
          </details>


          <!-- DUGMAD -->
          <div class="action-buttons">

            <button
              type="button"
              class="btn btn-info"
              @click.prevent="previousState()"
            >
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>
              <span v-text="$t('entity.action.back')">Назад</span>
            </button>

            <router-link
              v-if="transakcija.id"
              :to="{
                name: 'TransakcijaEdit',
                params: { transakcijaId: transakcija.id }
              }"
              tag="button"
              class="btn btn-primary"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
              <span v-text="$t('entity.action.edit')">Измена</span>
            </router-link>

            <button
              type="button"
              class="btn btn-primary"
              @click="previousStan()"
            >
              <span v-text="$t('entity.action.previousracun')"></span>
            </button>

            <button
              type="button"
              class="btn btn-primary"
              @click="nextStan()"
            >
              <span v-text="$t('entity.action.nextracun')"></span>
            </button>

            <b-button
              variant="info"
              class="btn"
              @click="stampanje()"
            >
              <span v-text="$t('entity.action.stampanje')">
                Штампање
              </span>
            </b-button>

          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./transakcija-sve-details.component.ts">
</script>

<style>
.analiticka-kartica {
  padding-top: 15px;
  padding-bottom: 20px;
}

/* Podaci o korisniku */

.customer-panel {
  padding: 14px 16px;
  margin-bottom: 15px;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 6px;
}

.customer-item {
  margin-bottom: 6px;
  overflow-wrap: anywhere;
}

.customer-label {
  margin-right: 5px;
  font-weight: 600;
}

/* Filteri */

.filter-panel {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 14px;
  padding: 12px 16px;
  margin-bottom: 15px;
  background-color: #e6f2ff;
  border: 1px solid #c8e0f7;
  border-radius: 6px;
}

.filter-checkbox {
  display: flex;
  align-items: center;
  white-space: nowrap;
}

.filter-checkbox input {
  margin-right: 6px;
}

.filter-checkbox label {
  margin: 0;
  cursor: pointer;
}

.filter-description {
  flex: 1 1 240px;
  min-width: 180px;
}

.filter-button {
  flex: 0 0 auto;
}

.open-items-panel {
  display:flex;
  justify-content:space-between;
  align-items:center;
  gap:15px;
  padding:12px 16px;
  margin-bottom:15px;
  background:#fff4d6;
  border:1px solid #f0cf75;
  border-radius:6px;
}

.open-items-description{
  display:flex;
  flex-direction:column;
}

.open-items-description span{
  font-size:.9rem;
  color:#555;
}

.open-items-button{
  white-space:nowrap;
}

/* Tabela */

.transaction-table-wrapper {
  width: 100%;
  max-height: 500px;
  margin-bottom: 15px;
  overflow: auto;
  border: 1px solid #dee2e6;
  border-radius: 4px;
}

.transaction-table {
  min-width: 1050px;
  margin-bottom: 0;
}

.transaction-table th,
.transaction-table td {
  vertical-align: middle;
}

.transaction-table .sticky {
  position: sticky;
  top: 0;
  z-index: 2;
  background-color: #f2f2f2;
  white-space: nowrap;
}

.date-column {
  white-space: nowrap;
}

.description-column {
  min-width: 180px;
}

.description-cell {
  min-width: 180px;
  max-width: 320px;
  white-space: normal;
  overflow-wrap: anywhere;
}

.numeric-column,
.numeric-cell {
  min-width: 110px;
  text-align: right;
  white-space: nowrap;
}

.action-column,
.action-cell {
  min-width: 100px;
  text-align: center;
  white-space: nowrap;
}

.total-row {
  background-color: #f8f9fa;
}

.total-label {
  text-align: right;
}

.ivk-row {
  background-color: #ffe5b4 !important;
}

/* Dugmad */

.action-buttons {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
}

.action-buttons .btn {
  margin: 0;
}

/* Tablet */

@media (max-width: 991.98px) {
  .filter-panel {
    align-items: stretch;
  }

  .filter-description {
    flex-basis: 100%;
  }

  .filter-button {
    width: 100%;
  }

  .transaction-table-wrapper {
    max-height: 450px;
  }
}

/* Telefon */

@media (max-width: 575.98px) {
  .analiticka-kartica {
    padding-left: 8px;
    padding-right: 8px;
  }

  .jh-entity-heading {
    font-size: 1.5rem;
  }

  .customer-panel,
  .filter-panel {
    padding: 12px;
  }

  .customer-item {
    display: flex;
    flex-direction: column;
    margin-bottom: 12px;
  }

  .customer-label {
    margin-bottom: 2px;
  }

  .filter-panel {
    flex-direction: column;
    align-items: stretch;
    gap: 10px;
  }

  .filter-checkbox {
    width: 100%;
  }

  .filter-description {
    width: 100%;
    min-width: 0;
  }

  .filter-button {
    width: 100%;
  }

  .transaction-table-wrapper {
    max-height: 400px;
  }

  .action-buttons {
    flex-direction: column;
    align-items: stretch;
  }

  .action-buttons .btn {
    width: 100%;
  }

  .total-label {
    text-align: right;
  }
}
.ivk-uplata-row {
    background-color: #D9F7BE !important;   /* svetlo zelena */
}
.ivk-racun-row {
    background-color: #FFE5B4 !important;   /* svetlo narandžasta */
}


/* Pregled raspodele: stilovi ograniceni na otvorene stavke */
.payment-review > summary { cursor: pointer; padding: 9px 0; }
.payment-review-total { display: flex; justify-content: space-between; align-items: center; gap: 12px; flex-wrap: wrap; padding: 12px 15px; background: #f3f6f9; border: 1px solid #dce3ea; border-radius: 5px; }
.payment-review-total strong { font-size: 1.2rem; }
.payment-review-hint { margin: 12px 0; color: #58616b; }
.payment-review-table > tbody > tr > td { vertical-align: middle; }
.payment-review-detail-cell { min-width: 260px; }
.payment-review-detail-cell summary { cursor: pointer; color: #0056b3; font-weight: 600; }
.payment-review-detail-content { padding: 10px 0 4px; min-width: 310px; }
.payment-review-detail-content table { background: #fff; }
.payment-review-remainder { text-align: right; font-size: 0.9rem; }
</style>
<style>
.payment-review-table .open-items-sort { border: 0; background: transparent; padding: 0; color: inherit; font: inherit; font-weight: 600; cursor: pointer; text-align: inherit; }
.payment-review-table .open-items-sort:hover { text-decoration: underline; }
</style>
