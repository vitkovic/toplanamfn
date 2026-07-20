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
              <label for="sveGodine">Све године</label>
            </div>

            <div class="filter-checkbox">
              <input
                id="racuni"
                type="checkbox"
                v-model="racuni"
              />
              <label for="racuni">Рачуни</label>
            </div>

            <div class="filter-checkbox">
              <input
                id="uplate"
                type="checkbox"
                v-model="uplate"
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

          <hr class="new3" />

          <!-- TABELA -->
          <div
            v-if="
              transakcijaZbirno.transakcije &&
              transakcijaZbirno.transakcije.length > 0
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
				    :key="t.id"
				    :class="{
				        'ivk-racun-row': t.opisRacuna && t.opisRacuna.toLowerCase().includes('ivk'),
				        'ivk-uplata-row': t.opis && t.opis.toLowerCase().includes('ivk')
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
                    {{ t.saldo | currency('') }}
                  </td>

                  <td class="action-cell">
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
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ transakcijaZbirno.dugujeUkupno | currency('') }}
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ transakcijaZbirno.potrazujeUkupno | currency('') }}
                    </strong>
                  </td>

                  <td class="numeric-cell">
                    <strong>
                      {{ transakcijaZbirno.saldoUkupno | currency('') }}
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
            Нема трансакција за изабрани критеријум.
          </div>

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

</style>