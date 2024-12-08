<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.racun.home.createOrEditLabel" v-text="$t('toplanaApp.racun.home.createOrEditLabel')">Create or edit a Racun</h2>
                <div>
                    <div class="form-group" v-if="racun.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="racun.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.datumRacuna')" for="racun-datumRacuna">Datum Racuna</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="racun-datumRacuna"
                                    v-model="$v.racun.datumRacuna.$model"
                                    name="datumRacuna"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="racun-datumRacuna" type="text" class="form-control" name="datumRacuna"  :class="{'valid': !$v.racun.datumRacuna.$invalid, 'invalid': $v.racun.datumRacuna.$invalid }"
                            v-model="$v.racun.datumRacuna.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.period')" for="racun-period">Period</label>
                        <input type="text" class="form-control" name="period" id="racun-period"
                            :class="{'valid': !$v.racun.period.$invalid, 'invalid': $v.racun.period.$invalid }" v-model="$v.racun.period.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.utrosakVarijabilni')" for="racun-utrosakVarijabilni">Utrosak Varijabilni</label>
                        <input type="number" class="form-control" name="utrosakVarijabilni" id="racun-utrosakVarijabilni"
                            :class="{'valid': !$v.racun.utrosakVarijabilni.$invalid, 'invalid': $v.racun.utrosakVarijabilni.$invalid }" v-model.number="$v.racun.utrosakVarijabilni.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.utrosakFiksni')" for="racun-utrosakFiksni">Utrosak Fiksni</label>
                        <input type="number" class="form-control" name="utrosakFiksni" id="racun-utrosakFiksni"
                            :class="{'valid': !$v.racun.utrosakFiksni.$invalid, 'invalid': $v.racun.utrosakFiksni.$invalid }" v-model.number="$v.racun.utrosakFiksni.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.utrosakOdrzavanje')" for="racun-utrosakOdrzavanje">Utrosak Odrzavanje</label>
                        <input type="number" class="form-control" name="utrosakOdrzavanje" id="racun-utrosakOdrzavanje"
                            :class="{'valid': !$v.racun.utrosakOdrzavanje.$invalid, 'invalid': $v.racun.utrosakOdrzavanje.$invalid }" v-model.number="$v.racun.utrosakOdrzavanje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.stan')" for="racun-stan">Stan</label>
                        <select class="form-control" id="racun-stan" name="stan" v-model="racun.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="racun.stan && stanOption.id === racun.stan.id ? racun.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.racun.nacrtRacuna')" for="racun-nacrtRacuna">Nacrt Racuna</label>
                        <select class="form-control" id="racun-nacrtRacuna" name="nacrtRacuna" v-model="racun.nacrtRacuna">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="racun.nacrtRacuna && nacrtRacunaOption.id === racun.nacrtRacuna.id ? racun.nacrtRacuna : nacrtRacunaOption" v-for="nacrtRacunaOption in nacrtRacunas" :key="nacrtRacunaOption.id">{{nacrtRacunaOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.racun.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./racun-update.component.ts">
</script>
