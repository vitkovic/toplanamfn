<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.utuzenje.home.createOrEditLabel" v-text="$t('toplanaApp.utuzenje.home.createOrEditLabel')">Create or edit a Utuzenje</h2>
                <div>
                    <div class="form-group" v-if="utuzenje.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="utuzenje.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.utuzenje.datum')" for="utuzenje-datum">Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="utuzenje-datum"
                                    v-model="$v.utuzenje.datum.$model"
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
                            <b-form-input id="utuzenje-datum" type="text" class="form-control" name="datum"  :class="{'valid': !$v.utuzenje.datum.$invalid, 'invalid': $v.utuzenje.datum.$invalid }"
                            v-model="$v.utuzenje.datum.$model"  required />
                        </b-input-group>
                        <div v-if="$v.utuzenje.datum.$anyDirty && $v.utuzenje.datum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.utuzenje.datum.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.utuzenje.stan')" for="utuzenje-stan">Stan</label>
                        <select class="form-control" id="utuzenje-stan" name="stan" v-model="utuzenje.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="utuzenje.stan && stanOption.id === utuzenje.stan.id ? utuzenje.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.sifra}} - {{stanOption.vlasnik.prezime}} {{stanOption.vlasnik.ime}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.utuzenje.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./utuzenje-update.component.ts">
</script>
