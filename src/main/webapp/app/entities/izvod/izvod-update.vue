<template>
    <div class="row justify-content-center">
        <div class="col-8">

            <b-form-file style="margin-bottom:30px"
                v-model="file1"
                :state="Boolean(file1)"
                placeholder="Изаберите датотеку са изводом или је превуците..."
                drop-placeholder="Drop file here..."
                browse-text="Изаберите"
            ></b-form-file>
            <div style="margin-bottom:200px">
                <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                </button>
                <button type="button" id="save-entity"  class="btn btn-primary" v-on:click="save()">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                </button>
            </div>

            <!--
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.izvod.home.createOrEditLabel" v-text="$t('toplanaApp.izvod.home.createOrEditLabel')">Create or edit a Izvod</h2>
                <div>
                    <div class="form-group" v-if="izvod.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="izvod.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.izvod.datum')" for="izvod-datum">Datum</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="izvod-datum"
                                    v-model="$v.izvod.datum.$model"
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
                            <b-form-input id="izvod-datum" type="text" class="form-control" name="datum"  :class="{'valid': !$v.izvod.datum.$invalid, 'invalid': $v.izvod.datum.$invalid }"
                            v-model="$v.izvod.datum.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.izvod.broj')" for="izvod-broj">Broj</label>
                        <input type="number" class="form-control" name="broj" id="izvod-broj"
                            :class="{'valid': !$v.izvod.broj.$invalid, 'invalid': $v.izvod.broj.$invalid }" v-model.number="$v.izvod.broj.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.izvod.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
            -->
        </div>
    </div>
</template>
<script lang="ts" src="./izvod-update.component.ts">
</script>
