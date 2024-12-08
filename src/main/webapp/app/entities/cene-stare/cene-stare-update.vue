<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.ceneStare.home.createOrEditLabel" v-text="$t('toplanaApp.ceneStare.home.createOrEditLabel')">Create or edit a CeneStare</h2>
                <div>
                    <div class="form-group" v-if="ceneStare.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="ceneStare.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ceneStare.kwh')" for="cene-stare-kwh">Kwh</label>
                        <input type="number" class="form-control" name="kwh" id="cene-stare-kwh"
                            :class="{'valid': !$v.ceneStare.kwh.$invalid, 'invalid': $v.ceneStare.kwh.$invalid }" v-model.number="$v.ceneStare.kwh.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ceneStare.fix')" for="cene-stare-fix">Fix</label>
                        <input type="number" class="form-control" name="fix" id="cene-stare-fix"
                            :class="{'valid': !$v.ceneStare.fix.$invalid, 'invalid': $v.ceneStare.fix.$invalid }" v-model.number="$v.ceneStare.fix.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ceneStare.odrzavanje')" for="cene-stare-odrzavanje">Odrzavanje</label>
                        <input type="number" class="form-control" name="odrzavanje" id="cene-stare-odrzavanje"
                            :class="{'valid': !$v.ceneStare.odrzavanje.$invalid, 'invalid': $v.ceneStare.odrzavanje.$invalid }" v-model.number="$v.ceneStare.odrzavanje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.ceneStare.ostalo')" for="cene-stare-ostalo">Ostalo</label>
                        <input type="number" class="form-control" name="ostalo" id="cene-stare-ostalo"
                            :class="{'valid': !$v.ceneStare.ostalo.$invalid, 'invalid': $v.ceneStare.ostalo.$invalid }" v-model.number="$v.ceneStare.ostalo.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.ceneStare.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cene-stare-update.component.ts">
</script>
