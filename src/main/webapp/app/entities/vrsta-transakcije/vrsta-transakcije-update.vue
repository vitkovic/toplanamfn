<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.vrstaTransakcije.home.createOrEditLabel" v-text="$t('toplanaApp.vrstaTransakcije.home.createOrEditLabel')">Create or edit a VrstaTransakcije</h2>
                <div>
                    <div class="form-group" v-if="vrstaTransakcije.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="vrstaTransakcije.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vrstaTransakcije.vrsta')" for="vrsta-transakcije-vrsta">Vrsta</label>
                        <input type="text" class="form-control" name="vrsta" id="vrsta-transakcije-vrsta"
                            :class="{'valid': !$v.vrstaTransakcije.vrsta.$invalid, 'invalid': $v.vrstaTransakcije.vrsta.$invalid }" v-model="$v.vrstaTransakcije.vrsta.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vrstaTransakcije.dodajeVrednost')" for="vrsta-transakcije-dodajeVrednost">Dodaje Vrednost</label>
                        <input type="number" class="form-control" name="dodajeVrednost" id="vrsta-transakcije-dodajeVrednost"
                            :class="{'valid': !$v.vrstaTransakcije.dodajeVrednost.$invalid, 'invalid': $v.vrstaTransakcije.dodajeVrednost.$invalid }" v-model.number="$v.vrstaTransakcije.dodajeVrednost.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.vrstaTransakcije.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./vrsta-transakcije-update.component.ts">
</script>
