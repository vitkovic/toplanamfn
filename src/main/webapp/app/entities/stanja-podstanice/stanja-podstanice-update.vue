<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stanjaPodstanice.home.createOrEditLabel" v-text="$t('toplanaApp.stanjaPodstanice.home.createOrEditLabel')">Create or edit a StanjaPodstanice</h2>
                <div>
                    <div class="form-group" v-if="stanjaPodstanice.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stanjaPodstanice.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstanice.stanje')" for="stanja-podstanice-stanje">Stanje</label>
                        <input type="number" class="form-control" name="stanje" id="stanja-podstanice-stanje"
                            :class="{'valid': !$v.stanjaPodstanice.stanje.$invalid, 'invalid': $v.stanjaPodstanice.stanje.$invalid }" v-model.number="$v.stanjaPodstanice.stanje.$model"  required/>
                        <div v-if="$v.stanjaPodstanice.stanje.$anyDirty && $v.stanjaPodstanice.stanje.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stanjaPodstanice.stanje.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stanjaPodstanice.stanje.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstanice.datumOcitavanja')" for="stanja-podstanice-datumOcitavanja">Datum Ocitavanja</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="stanja-podstanice-datumOcitavanja"
                                    v-model="$v.stanjaPodstanice.datumOcitavanja.$model"
                                    name="datumOcitavanja"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="stanja-podstanice-datumOcitavanja" type="text" class="form-control" name="datumOcitavanja"  :class="{'valid': !$v.stanjaPodstanice.datumOcitavanja.$invalid, 'invalid': $v.stanjaPodstanice.datumOcitavanja.$invalid }"
                            v-model="$v.stanjaPodstanice.datumOcitavanja.$model"  required />
                        </b-input-group>
                        <div v-if="$v.stanjaPodstanice.datumOcitavanja.$anyDirty && $v.stanjaPodstanice.datumOcitavanja.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stanjaPodstanice.datumOcitavanja.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstanice.podstanica')" for="stanja-podstanice-podstanica">Podstanica</label>
                        <select class="form-control" id="stanja-podstanice-podstanica" name="podstanica" v-model="stanjaPodstanice.podstanica">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stanjaPodstanice.podstanica && podstanicaOption.id === stanjaPodstanice.podstanica.id ? stanjaPodstanice.podstanica : podstanicaOption" v-for="podstanicaOption in podstanicas" :key="podstanicaOption.id">{{podstanicaOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stanjaPodstanice.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stanja-podstanice-update.component.ts">
</script>
