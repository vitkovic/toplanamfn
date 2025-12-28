<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.ugovorRate.home.createOrEditLabel" v-text="$t('toplanaApp.ugovorRate.home.createOrEditLabel')">Create or edit a UgovorRate</h2>
                <div>
                    <div class="form-group" v-if="ugovorRate.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="ugovorRate.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ugovorRate.brojRata')" for="ugovor-rate-brojRata">Broj Rata</label>
                        <input type="number" class="form-control" name="brojRata" id="ugovor-rate-brojRata"
                            :class="{'valid': !$v.ugovorRate.brojRata.$invalid, 'invalid': $v.ugovorRate.brojRata.$invalid }" v-model.number="$v.ugovorRate.brojRata.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ugovorRate.datumSklapanja')" for="ugovor-rate-datumSklapanja">Datum Sklapanja</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="ugovor-rate-datumSklapanja"
                                    v-model="$v.ugovorRate.datumSklapanja.$model"
                                    name="datumSklapanja"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="ugovor-rate-datumSklapanja" type="text" class="form-control" name="datumSklapanja"  :class="{'valid': !$v.ugovorRate.datumSklapanja.$invalid, 'invalid': $v.ugovorRate.datumSklapanja.$invalid }"
                            v-model="$v.ugovorRate.datumSklapanja.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ugovorRate.datumDospeca')" for="ugovor-rate-datumDospeca">Datum Dospeca</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="ugovor-rate-datumDospeca"
                                    v-model="$v.ugovorRate.datumDospeca.$model"
                                    name="datumDospeca"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="ugovor-rate-datumDospeca" type="text" class="form-control" name="datumDospeca"  :class="{'valid': !$v.ugovorRate.datumDospeca.$invalid, 'invalid': $v.ugovorRate.datumDospeca.$invalid }"
                            v-model="$v.ugovorRate.datumDospeca.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ugovorRate.stan')" for="ugovor-rate-stan">Stan</label>
                        <select class="form-control" id="ugovor-rate-stan" name="stan" v-model="ugovorRate.stan">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="ugovorRate.stan && stanOption.id === ugovorRate.stan.id ? ugovorRate.stan : stanOption" v-for="stanOption in stans" :key="stanOption.id">{{stanOption.sifra}} - {{stanOption.vlasnik.prezime}} {{stanOption.vlasnik.ime}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.ugovorRate.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ugovor-rate-update.component.ts">
</script>
