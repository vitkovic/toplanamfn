<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.sifraDokumenta.home.createOrEditLabel" v-text="$t('toplanaApp.sifraDokumenta.home.createOrEditLabel')">Create or edit a SifraDokumenta</h2>
                <div>
                    <div class="form-group" v-if="sifraDokumenta.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sifraDokumenta.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.sifraDokumenta.sifra')" for="sifra-dokumenta-sifra">Sifra</label>
                        <input type="text" class="form-control" name="sifra" id="sifra-dokumenta-sifra"
                            :class="{'valid': !$v.sifraDokumenta.sifra.$invalid, 'invalid': $v.sifraDokumenta.sifra.$invalid }" v-model="$v.sifraDokumenta.sifra.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sifraDokumenta.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sifra-dokumenta-update.component.ts">
</script>
