<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stavkeUtuzenja.home.createOrEditLabel" v-text="$t('toplanaApp.stavkeUtuzenja.home.createOrEditLabel')">Create or edit a StavkeUtuzenja</h2>
                <div>
                    <div class="form-group" v-if="stavkeUtuzenja.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stavkeUtuzenja.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.obracunskiPeriod')" for="stavke-utuzenja-obracunskiPeriod">Obracunski Period</label>
                        <input type="text" class="form-control" name="obracunskiPeriod" id="stavke-utuzenja-obracunskiPeriod"
                            :class="{'valid': !$v.stavkeUtuzenja.obracunskiPeriod.$invalid, 'invalid': $v.stavkeUtuzenja.obracunskiPeriod.$invalid }" v-model="$v.stavkeUtuzenja.obracunskiPeriod.$model"  required/>
                        <div v-if="$v.stavkeUtuzenja.obracunskiPeriod.$anyDirty && $v.stavkeUtuzenja.obracunskiPeriod.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.obracunskiPeriod.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.datumIzdavanjaRacuna')" for="stavke-utuzenja-datumIzdavanjaRacuna">Datum Izdavanja Racuna</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="stavke-utuzenja-datumIzdavanjaRacuna"
                                    v-model="$v.stavkeUtuzenja.datumIzdavanjaRacuna.$model"
                                    name="datumIzdavanjaRacuna"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="stavke-utuzenja-datumIzdavanjaRacuna" type="text" class="form-control" name="datumIzdavanjaRacuna"  :class="{'valid': !$v.stavkeUtuzenja.datumIzdavanjaRacuna.$invalid, 'invalid': $v.stavkeUtuzenja.datumIzdavanjaRacuna.$invalid }"
                            v-model="$v.stavkeUtuzenja.datumIzdavanjaRacuna.$model"  required />
                        </b-input-group>
                        <div v-if="$v.stavkeUtuzenja.datumIzdavanjaRacuna.$anyDirty && $v.stavkeUtuzenja.datumIzdavanjaRacuna.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.datumIzdavanjaRacuna.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.datumDospecaRacuna')" for="stavke-utuzenja-datumDospecaRacuna">Datum Dospeca Racuna</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="stavke-utuzenja-datumDospecaRacuna"
                                    v-model="$v.stavkeUtuzenja.datumDospecaRacuna.$model"
                                    name="datumDospecaRacuna"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="stavke-utuzenja-datumDospecaRacuna" type="text" class="form-control" name="datumDospecaRacuna"  :class="{'valid': !$v.stavkeUtuzenja.datumDospecaRacuna.$invalid, 'invalid': $v.stavkeUtuzenja.datumDospecaRacuna.$invalid }"
                            v-model="$v.stavkeUtuzenja.datumDospecaRacuna.$model"  required />
                        </b-input-group>
                        <div v-if="$v.stavkeUtuzenja.datumDospecaRacuna.$anyDirty && $v.stavkeUtuzenja.datumDospecaRacuna.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.datumDospecaRacuna.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.zaduzenje')" for="stavke-utuzenja-zaduzenje">Zaduzenje</label>
                        <input type="number" class="form-control" name="zaduzenje" id="stavke-utuzenja-zaduzenje"
                            :class="{'valid': !$v.stavkeUtuzenja.zaduzenje.$invalid, 'invalid': $v.stavkeUtuzenja.zaduzenje.$invalid }" v-model.number="$v.stavkeUtuzenja.zaduzenje.$model"  required/>
                        <div v-if="$v.stavkeUtuzenja.zaduzenje.$anyDirty && $v.stavkeUtuzenja.zaduzenje.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.zaduzenje.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.zaduzenje.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.ukupnoZaUplatu')" for="stavke-utuzenja-ukupnoZaUplatu">Ukupno Za Uplatu</label>
                        <input type="number" class="form-control" name="ukupnoZaUplatu" id="stavke-utuzenja-ukupnoZaUplatu"
                            :class="{'valid': !$v.stavkeUtuzenja.ukupnoZaUplatu.$invalid, 'invalid': $v.stavkeUtuzenja.ukupnoZaUplatu.$invalid }" v-model.number="$v.stavkeUtuzenja.ukupnoZaUplatu.$model"  required/>
                        <div v-if="$v.stavkeUtuzenja.ukupnoZaUplatu.$anyDirty && $v.stavkeUtuzenja.ukupnoZaUplatu.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.ukupnoZaUplatu.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stavkeUtuzenja.ukupnoZaUplatu.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stavkeUtuzenja.utuzenje')" for="stavke-utuzenja-utuzenje">Utuzenje</label>
                        <select class="form-control" id="stavke-utuzenja-utuzenje" name="utuzenje" v-model="stavkeUtuzenja.utuzenje">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stavkeUtuzenja.utuzenje && utuzenjeOption.id === stavkeUtuzenja.utuzenje.id ? stavkeUtuzenja.utuzenje : utuzenjeOption" v-for="utuzenjeOption in utuzenjes" :key="utuzenjeOption.id">{{utuzenjeOption.stan.sifra}} - {{utuzenjeOption.stan.vlasnik.prezime}} {{utuzenjeOption.stan.vlasnik.ime}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stavkeUtuzenja.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stavke-utuzenja-update.component.ts">
</script>
