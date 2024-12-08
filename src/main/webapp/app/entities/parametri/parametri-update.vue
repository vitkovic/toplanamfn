<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.parametri.home.createOrEditLabel" v-text="$t('toplanaApp.parametri.home.createOrEditLabel')">Create or edit a Parametri</h2>
                <div>
                    <div class="form-group" v-if="parametri.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="parametri.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametri.naziv')" for="parametri-naziv">Naziv</label>
                        <input type="text" class="form-control" name="naziv" id="parametri-naziv"
                            :class="{'valid': !$v.parametri.naziv.$invalid, 'invalid': $v.parametri.naziv.$invalid }" v-model="$v.parametri.naziv.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.parametri.vrednost')" for="parametri-vrednost">Vrednost</label>
                        <input type="number" class="form-control" name="vrednost" id="parametri-vrednost"
                            :class="{'valid': !$v.parametri.vrednost.$invalid, 'invalid': $v.parametri.vrednost.$invalid }" v-model.number="$v.parametri.vrednost.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.parametri.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./parametri-update.component.ts">
</script>
