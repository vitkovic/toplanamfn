<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.vlasnik.home.createOrEditLabel" v-text="$t('toplanaApp.vlasnik.home.createOrEditLabel')">Create or edit a Vlasnik</h2>
                <div>
                    <div class="form-group" v-if="vlasnik.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="vlasnik.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.ime')" for="vlasnik-ime">Ime</label>
                        <input type="text" class="form-control" name="ime" id="vlasnik-ime"
                            :class="{'valid': !$v.vlasnik.ime.$invalid, 'invalid': $v.vlasnik.ime.$invalid }" v-model="$v.vlasnik.ime.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.prezime')" for="vlasnik-prezime">Prezime</label>
                        <input type="text" class="form-control" name="prezime" id="vlasnik-prezime"
                            :class="{'valid': !$v.vlasnik.prezime.$invalid, 'invalid': $v.vlasnik.prezime.$invalid }" v-model="$v.vlasnik.prezime.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.brojRacuna')" for="vlasnik-brojRacuna">Broj Racuna</label>
                        <input type="text" class="form-control" name="brojRacuna" id="vlasnik-brojRacuna"
                            :class="{'valid': !$v.vlasnik.brojRacuna.$invalid, 'invalid': $v.vlasnik.brojRacuna.$invalid }" v-model="$v.vlasnik.brojRacuna.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.partijaRacuna')" for="vlasnik-partijaRacuna">Partija Racuna</label>
                        <input type="text" class="form-control" name="partijaRacuna" id="vlasnik-partijaRacuna"
                            :class="{'valid': !$v.vlasnik.partijaRacuna.$invalid, 'invalid': $v.vlasnik.partijaRacuna.$invalid }" v-model="$v.vlasnik.partijaRacuna.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.naziv')" for="vlasnik-naziv">Naziv</label>
                        <input type="text" class="form-control" name="naziv" id="vlasnik-naziv"
                            :class="{'valid': !$v.vlasnik.naziv.$invalid, 'invalid': $v.vlasnik.naziv.$invalid }" v-model="$v.vlasnik.naziv.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.vlasnik.email')" for="vlasnik-email">Email</label>
                        <input type="text" class="form-control" name="email" id="vlasnik-email"
                            :class="{'valid': !$v.vlasnik.email.$invalid, 'invalid': $v.vlasnik.email.$invalid }" v-model="$v.vlasnik.email.$model" />
                        <div v-if="$v.vlasnik.email.$anyDirty && $v.vlasnik.email.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vlasnik.email.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Email'})">
                                This field should follow pattern for "Email".
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.vlasnik.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./vlasnik-update.component.ts">
</script>
