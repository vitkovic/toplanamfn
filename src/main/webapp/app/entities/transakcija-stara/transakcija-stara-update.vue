<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.transakcijaStara.home.createOrEditLabel" v-text="$t('toplanaApp.transakcijaStara.home.createOrEditLabel')">Create or edit a TransakcijaStara</h2>
                <div>
                    <div class="form-group" v-if="transakcijaStara.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="transakcijaStara.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.datum')" for="transakcija-stara-datum">Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="transakcija-stara-datum"
                                    v-model="$v.transakcijaStara.datum.$model"
                                    name="datum"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="transakcija-stara-datum" type="text" class="form-control" name="datum"  :class="{'valid': !$v.transakcijaStara.datum.$invalid, 'invalid': $v.transakcijaStara.datum.$invalid }"
                            v-model="$v.transakcijaStara.datum.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.valuta')" for="transakcija-stara-valuta">Valuta</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="transakcija-stara-valuta"
                                    v-model="$v.transakcijaStara.valuta.$model"
                                    name="valuta"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="transakcija-stara-valuta" type="text" class="form-control" name="valuta"  :class="{'valid': !$v.transakcijaStara.valuta.$invalid, 'invalid': $v.transakcijaStara.valuta.$invalid }"
                            v-model="$v.transakcijaStara.valuta.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.status')" for="transakcija-stara-status">Status</label>
                        <input type="text" class="form-control" name="status" id="transakcija-stara-status"
                            :class="{'valid': !$v.transakcijaStara.status.$invalid, 'invalid': $v.transakcijaStara.status.$invalid }" v-model="$v.transakcijaStara.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.opis')" for="transakcija-stara-opis">Opis</label>
                        <input type="text" class="form-control" name="opis" id="transakcija-stara-opis"
                            :class="{'valid': !$v.transakcijaStara.opis.$invalid, 'invalid': $v.transakcijaStara.opis.$invalid }" v-model="$v.transakcijaStara.opis.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.duguje')" for="transakcija-stara-duguje">Duguje</label>
                        <input type="number" class="form-control" name="duguje" id="transakcija-stara-duguje"
                            :class="{'valid': !$v.transakcijaStara.duguje.$invalid, 'invalid': $v.transakcijaStara.duguje.$invalid }" v-model.number="$v.transakcijaStara.duguje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.potrazuje')" for="transakcija-stara-potrazuje">Potrazuje</label>
                        <input type="number" class="form-control" name="potrazuje" id="transakcija-stara-potrazuje"
                            :class="{'valid': !$v.transakcijaStara.potrazuje.$invalid, 'invalid': $v.transakcijaStara.potrazuje.$invalid }" v-model.number="$v.transakcijaStara.potrazuje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.vrstaTransakcije')" for="transakcija-stara-vrstaTransakcije">Vrsta Transakcije</label>
                        <select class="form-control" id="transakcija-stara-vrstaTransakcije" name="vrstaTransakcije" v-model="transakcijaStara.vrstaTransakcije">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcijaStara.vrstaTransakcije && vrstaTransakcijeOption.id === transakcijaStara.vrstaTransakcije.id ? transakcijaStara.vrstaTransakcije : vrstaTransakcijeOption" v-for="vrstaTransakcijeOption in vrstaTransakcijes" :key="vrstaTransakcijeOption.id">{{vrstaTransakcijeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.sifraDokumenta')" for="transakcija-stara-sifraDokumenta">Sifra Dokumenta</label>
                        <select class="form-control" id="transakcija-stara-sifraDokumenta" name="sifraDokumenta" v-model="transakcijaStara.sifraDokumenta">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcijaStara.sifraDokumenta && sifraDokumentaOption.id === transakcijaStara.sifraDokumenta.id ? transakcijaStara.sifraDokumenta : sifraDokumentaOption" v-for="sifraDokumentaOption in sifraDokumentas" :key="sifraDokumentaOption.id">{{sifraDokumentaOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.sifraPromene')" for="transakcija-stara-sifraPromene">Sifra Promene</label>
                        <select class="form-control" id="transakcija-stara-sifraPromene" name="sifraPromene" v-model="transakcijaStara.sifraPromene">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcijaStara.sifraPromene && sifraPromeneOption.id === transakcijaStara.sifraPromene.id ? transakcijaStara.sifraPromene : sifraPromeneOption" v-for="sifraPromeneOption in sifraPromenes" :key="sifraPromeneOption.id">{{sifraPromeneOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.cene')" for="transakcija-stara-cene">Cene</label>
                        <select class="form-control" id="transakcija-stara-cene" name="cene" v-model="transakcijaStara.cene">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcijaStara.cene && ceneStareOption.id === transakcijaStara.cene.id ? transakcijaStara.cene : ceneStareOption" v-for="ceneStareOption in ceneStares" :key="ceneStareOption.id">{{ceneStareOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcijaStara.stan')" for="transakcija-stara-stan">Stan</label>
                        <select class="form-control" id="transakcija-stara-stan" name="stan" v-model="transakcijaStara.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcijaStara.stan && stanOption.id === transakcijaStara.stan.id ? transakcijaStara.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.transakcijaStara.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./transakcija-stara-update.component.ts">
</script>
