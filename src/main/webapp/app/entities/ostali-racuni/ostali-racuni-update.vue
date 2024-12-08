<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.ostaliRacuni.home.createOrEditLabel" v-text="$t('toplanaApp.ostaliRacuni.home.createOrEditLabel')">Create or edit a ostaliRacuni</h2>
                <div>
                    <div class="form-group" v-if="ostaliRacuni.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="ostaliRacuni.id" readonly />
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.ostaliRacuni.naziv')" for="ostaliRacuni-naziv"></label>
                                <input type="text" class="form-control" name="naziv" id="ostaliRacuni-naziv"
                                    :class="{'valid': !$v.ostaliRacuni.naziv.$invalid, 'invalid': $v.ostaliRacuni.naziv.$invalid }" v-model="$v.ostaliRacuni.naziv.$model" />
                                <div v-if="$v.ostaliRacuni.naziv.$anyDirty && $v.ostaliRacuni.naziv.$invalid">
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.ostaliRacuni.sifra')" for="ostaliRacuni-sifra">Sifra</label>
                                <input type="text" class="form-control" name="sifra" id="ostaliRacuni-sifra"
                                    :class="{'valid': !$v.ostaliRacuni.sifra.$invalid, 'invalid': $v.ostaliRacuni.sifra.$invalid }" v-model="$v.ostaliRacuni.sifra.$model" />
                                <div v-if="$v.ostaliRacuni.sifra.$anyDirty && $v.ostaliRacuni.sifra.$invalid">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.ostaliRacuni.aktivan')" for="ostaliRacuni-aktivan"></label>
                                <input type="checkbox" class="form-check" name="aktivan" id="racun-aktivan"
                                    :class="{'valid': !$v.ostaliRacuni.aktivan.$invalid, 'invalid': $v.ostaliRacuni.aktivan.$invalid }" v-model="$v.ostaliRacuni.aktivan.$model" />
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.opomena.stan')" for="opomena-stan">Stan</label>
                                <select class="form-control" id="ostaliRacuni-stan" name="stan" v-model="ostaliRacuni.stan">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="ostaliRacuni.stan && stanOption.id === ostaliRacuni.stan.id ? ostaliRacuni.stan : stanOption" 
                                        v-for="stanOption in stans" :key="stanOption.id">{{stanOption.sifra}} {{stanOption.vlasnik.prezime}} {{stanOption.vlasnik.ime}}
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                    
                    
                </div>
                <div style="margin-bottom:150px">
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.ostaliRacuni.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ostali-racuni-update.component.ts">
</script>
