<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.opomena.home.createOrEditLabel" v-text="$t('toplanaApp.opomena.home.createOrEditLabel')">Create or edit a Opomena</h2>
                <div>
                    <div class="form-group" v-if="opomena.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="opomena.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.opomena.datumOpomene')" for="opomena-datumOpomene">Datum Opomene</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="opomena-datumOpomene"
                                    v-model="$v.opomena.datumOpomene.$model"
                                    name="datumOpomene"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="opomena-datumOpomene" type="text" class="form-control" name="datumOpomene"  :class="{'valid': !$v.opomena.datumOpomene.$invalid, 'invalid': $v.opomena.datumOpomene.$invalid }"
                            v-model="$v.opomena.datumOpomene.$model"  required />
                        </b-input-group>
                        <div v-if="$v.opomena.datumOpomene.$anyDirty && $v.opomena.datumOpomene.$invalid">
                            <small class="form-text text-danger" v-if="!$v.opomena.datumOpomene.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.opomena.zakljucniDatum')" for="opomena-zakljucniDatum">Zakljucni Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="opomena-zakljucniDatum"
                                    v-model="$v.opomena.zakljucniDatum.$model"
                                    name="zakljucniDatum"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="opomena-zakljucniDatum" type="text" class="form-control" name="zakljucniDatum"  :class="{'valid': !$v.opomena.zakljucniDatum.$invalid, 'invalid': $v.opomena.zakljucniDatum.$invalid }"
                            v-model="$v.opomena.zakljucniDatum.$model"  required />
                        </b-input-group>
                        <div v-if="$v.opomena.zakljucniDatum.$anyDirty && $v.opomena.zakljucniDatum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.opomena.zakljucniDatum.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.opomena.iznos')" for="opomena-iznos">Iznos</label>
                        <input type="number" class="form-control" name="iznos" id="opomena-iznos"
                            :class="{'valid': !$v.opomena.iznos.$invalid, 'invalid': $v.opomena.iznos.$invalid }" v-model.number="$v.opomena.iznos.$model"  required/>
                        <div v-if="$v.opomena.iznos.$anyDirty && $v.opomena.iznos.$invalid">
                            <small class="form-text text-danger" v-if="!$v.opomena.iznos.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.opomena.iznos.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.opomena.datumIzmirenja')" for="opomena-datumIzmirenja">Datum Izmirenja</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="opomena-datumIzmirenja"
                                    v-model="$v.opomena.datumIzmirenja.$model"
                                    name="datumIzmirenja"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="opomena-datumIzmirenja" type="text" class="form-control" name="datumIzmirenja"  :class="{'valid': !$v.opomena.datumIzmirenja.$invalid, 'invalid': $v.opomena.datumIzmirenja.$invalid }"
                            v-model="$v.opomena.datumIzmirenja.$model"  required />
                        </b-input-group>
                        <div v-if="$v.opomena.datumIzmirenja.$anyDirty && $v.opomena.datumIzmirenja.$invalid">
                            <small class="form-text text-danger" v-if="!$v.opomena.datumIzmirenja.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.opomena.stan')" for="opomena-stan">Stan</label>
                        <select class="form-control" id="opomena-stan" name="stan" v-model="opomena.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="opomena.stan && stanOption.id === opomena.stan.id ? opomena.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.opomena.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./opomena-update.component.ts">
</script>
