<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.transakcija.home.createOrEditLabel" v-text="$t('toplanaApp.transakcija.home.createOrEditLabel')">Create or edit a Transakcija</h2>
                <div>
                    <div class="form-group" v-if="transakcija.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="transakcija.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.datum')" for="transakcija-datum">Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="transakcija-datum"
                                    v-model="$v.transakcija.datum.$model"
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
                            <b-form-input id="transakcija-datum" type="text" class="form-control" name="datum"  :class="{'valid': !$v.transakcija.datum.$invalid, 'invalid': $v.transakcija.datum.$invalid }"
                            v-model="$v.transakcija.datum.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.valuta')" for="transakcija-valuta">Valuta</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="transakcija-valuta"
                                    v-model="$v.transakcija.valuta.$model"
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
                            <b-form-input id="transakcija-valuta" type="text" class="form-control" name="valuta"  :class="{'valid': !$v.transakcija.valuta.$invalid, 'invalid': $v.transakcija.valuta.$invalid }"
                            v-model="$v.transakcija.valuta.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.status')" for="transakcija-status">Status</label>
                        <input type="text" class="form-control" name="status" id="transakcija-status"
                            :class="{'valid': !$v.transakcija.status.$invalid, 'invalid': $v.transakcija.status.$invalid }" v-model="$v.transakcija.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.opis')" for="transakcija-opis">Opis</label>
                        <input type="text" class="form-control" name="opis" id="transakcija-opis"
                            :class="{'valid': !$v.transakcija.opis.$invalid, 'invalid': $v.transakcija.opis.$invalid }" v-model="$v.transakcija.opis.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.duguje')" for="transakcija-duguje">Duguje</label>
                        <input type="number" class="form-control" name="duguje" id="transakcija-duguje"
                            :class="{'valid': !$v.transakcija.duguje.$invalid, 'invalid': $v.transakcija.duguje.$invalid }" v-model.number="$v.transakcija.duguje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.potrazuje')" for="transakcija-potrazuje">Potrazuje</label>
                        <input type="number" class="form-control" name="potrazuje" id="transakcija-potrazuje"
                            :class="{'valid': !$v.transakcija.potrazuje.$invalid, 'invalid': $v.transakcija.potrazuje.$invalid }" v-model.number="$v.transakcija.potrazuje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.vrstaTransakcije')" for="transakcija-vrstaTransakcije">Vrsta Transakcije</label>
                        <select class="form-control" id="transakcija-vrstaTransakcije" name="vrstaTransakcije" v-model="transakcija.vrstaTransakcije">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.vrstaTransakcije && vrstaTransakcijeOption.id === transakcija.vrstaTransakcije.id ? transakcija.vrstaTransakcije : vrstaTransakcijeOption" v-for="vrstaTransakcijeOption in vrstaTransakcijes" :key="vrstaTransakcijeOption.id">{{vrstaTransakcijeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.sifraDokumenta')" for="transakcija-sifraDokumenta">Sifra Dokumenta</label>
                        <select class="form-control" id="transakcija-sifraDokumenta" name="sifraDokumenta" v-model="transakcija.sifraDokumenta">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.sifraDokumenta && sifraDokumentaOption.id === transakcija.sifraDokumenta.id ? transakcija.sifraDokumenta : sifraDokumentaOption" v-for="sifraDokumentaOption in sifraDokumentas" :key="sifraDokumentaOption.id">{{sifraDokumentaOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.sifraPromene')" for="transakcija-sifraPromene">Sifra Promene</label>
                        <select class="form-control" id="transakcija-sifraPromene" name="sifraPromene" v-model="transakcija.sifraPromene">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.sifraPromene && sifraPromeneOption.id === transakcija.sifraPromene.id ? transakcija.sifraPromene : sifraPromeneOption" v-for="sifraPromeneOption in sifraPromenes" :key="sifraPromeneOption.id">{{sifraPromeneOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.cene')" for="transakcija-cene">Cene</label>
                        <select class="form-control" id="transakcija-cene" name="cene" v-model="transakcija.cene">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.cene && ceneOption.id === transakcija.cene.id ? transakcija.cene : ceneOption" v-for="ceneOption in cenes" :key="ceneOption.id">{{ceneOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.transakcija.stan')" for="transakcija-stan">Stan</label>
                        <select class="form-control" id="transakcija-stan" name="stan" v-model="transakcija.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.stan && stanOption.id === transakcija.stan.id ? transakcija.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.transakcija.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./transakcija-update.component.ts">
</script>
