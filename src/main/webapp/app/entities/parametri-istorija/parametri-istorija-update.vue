<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.parametriIstorija.home.createOrEditLabel" v-text="$t('toplanaApp.parametriIstorija.home.createOrEditLabel')">Create or edit a ParametriIstorija</h2>
                <div>
                    <div class="form-group" v-if="parametriIstorija.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="parametriIstorija.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametriIstorija.vrednost')" for="parametri-istorija-vrednost">Vrednost</label>
                        <input type="number" class="form-control" name="vrednost" id="parametri-istorija-vrednost"
                            :class="{'valid': !$v.parametriIstorija.vrednost.$invalid, 'invalid': $v.parametriIstorija.vrednost.$invalid }" v-model.number="$v.parametriIstorija.vrednost.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametriIstorija.vaziOd')" for="parametri-istorija-vaziOd">Vazi Od</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="parametri-istorija-vaziOd"
                                    v-model="$v.parametriIstorija.vaziOd.$model"
                                    name="vaziOd"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="parametri-istorija-vaziOd" type="text" class="form-control" name="vaziOd"  :class="{'valid': !$v.parametriIstorija.vaziOd.$invalid, 'invalid': $v.parametriIstorija.vaziOd.$invalid }"
                            v-model="$v.parametriIstorija.vaziOd.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametriIstorija.vaziDo')" for="parametri-istorija-vaziDo">Vazi Do</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="parametri-istorija-vaziDo"
                                    v-model="$v.parametriIstorija.vaziDo.$model"
                                    name="vaziDo"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="parametri-istorija-vaziDo" type="text" class="form-control" name="vaziDo"  :class="{'valid': !$v.parametriIstorija.vaziDo.$invalid, 'invalid': $v.parametriIstorija.vaziDo.$invalid }"
                            v-model="$v.parametriIstorija.vaziDo.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametriIstorija.parametri')" for="parametri-istorija-parametri">Parametri</label>
                        <select class="form-control" id="parametri-istorija-parametri" name="parametri" v-model="parametriIstorija.parametri">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="parametriIstorija.parametri && parametriOption.id === parametriIstorija.parametri.id ? parametriIstorija.parametri : parametriOption" v-for="parametriOption in parametris" :key="parametriOption.id">{{parametriOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.parametriIstorija.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./parametri-istorija-update.component.ts">
</script>
