<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.podstanica.home.createOrEditLabel" v-text="$t('toplanaApp.podstanica.home.createOrEditLabel')">Create or edit a Podstanica</h2>
                <div>
                    <div class="form-group" v-if="podstanica.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="podstanica.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.podstanica.naziv')" for="podstanica-naziv">Naziv</label>
                        <input type="text" class="form-control" name="naziv" id="podstanica-naziv"
                            :class="{'valid': !$v.podstanica.naziv.$invalid, 'invalid': $v.podstanica.naziv.$invalid }" v-model="$v.podstanica.naziv.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.podstanica.broj')" for="podstanica-broj">Naziv</label>
                        <input type="number" class="form-control" name="broj" id="podstanica-broj"
                             v-model="podstanica.broj" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.podstanica.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./podstanica-update.component.ts">
</script>
