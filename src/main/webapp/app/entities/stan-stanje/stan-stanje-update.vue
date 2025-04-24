<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stanStanje.home.createOrEditLabel" v-text="$t('toplanaApp.stanStanje.home.createOrEditLabel')">Create or edit a StanStanje</h2>
                <div>
                    <div class="form-group" v-if="stanStanje.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stanStanje.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanStanje.sifra')" for="stan-stanje-sifra">Sifra</label>
                        <input type="text" class="form-control" name="sifra" id="stan-stanje-sifra"
                            :class="{'valid': !$v.stanStanje.sifra.$invalid, 'invalid': $v.stanStanje.sifra.$invalid }" v-model="$v.stanStanje.sifra.$model"  required/>
                        <div v-if="$v.stanStanje.sifra.$anyDirty && $v.stanStanje.sifra.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stanStanje.sifra.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanStanje.datum')" for="stan-stanje-datum">Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="stan-stanje-datum"
                                    v-model="$v.stanStanje.datum.$model"
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
                            <b-form-input id="stan-stanje-datum" type="text" class="form-control" name="datum"  :class="{'valid': !$v.stanStanje.datum.$invalid, 'invalid': $v.stanStanje.datum.$invalid }"
                            v-model="$v.stanStanje.datum.$model"  required />
                        </b-input-group>
                        <div v-if="$v.stanStanje.datum.$anyDirty && $v.stanStanje.datum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stanStanje.datum.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanStanje.vrednost')" for="stan-stanje-vrednost">Vrednost</label>
                        <input type="number" class="form-control" name="vrednost" id="stan-stanje-vrednost"
                            :class="{'valid': !$v.stanStanje.vrednost.$invalid, 'invalid': $v.stanStanje.vrednost.$invalid }" v-model.number="$v.stanStanje.vrednost.$model"  required/>
                        <div v-if="$v.stanStanje.vrednost.$anyDirty && $v.stanStanje.vrednost.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stanStanje.vrednost.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stanStanje.vrednost.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanStanje.stan_id')" for="stan-stanje-stan_id">Stan Id</label>
                        <input type="number" class="form-control" name="stan_id" id="stan-stanje-stan_id"
                            :class="{'valid': !$v.stanStanje.stan_id.$invalid, 'invalid': $v.stanStanje.stan_id.$invalid }" v-model.number="$v.stanStanje.stan_id.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanStanje.stan')" for="stan-stanje-stan">Stan</label>
                        <select class="form-control" id="stan-stanje-stan" name="stan" v-model="stanStanje.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stanStanje.stan && stanOption.id === stanStanje.stan.id ? stanStanje.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stanStanje.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stan-stanje-update.component.ts">
</script>
