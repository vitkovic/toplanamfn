<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stanjaPodstaniceZaRacun.home.createOrEditLabel" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.home.createOrEditLabel')">Create or edit a StanjaPodstaniceZaRacun</h2>
                <div>
                    <div class="form-group" v-if="stanjaPodstaniceZaRacun.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stanjaPodstaniceZaRacun.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.staroStanje')" for="stanja-podstanice-za-racun-staroStanje">Staro Stanje</label>
                        <input type="number" class="form-control" name="staroStanje" id="stanja-podstanice-za-racun-staroStanje"
                            :class="{'valid': !$v.stanjaPodstaniceZaRacun.staroStanje.$invalid, 'invalid': $v.stanjaPodstaniceZaRacun.staroStanje.$invalid }" v-model.number="$v.stanjaPodstaniceZaRacun.staroStanje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.novoStanje')" for="stanja-podstanice-za-racun-novoStanje">Novo Stanje</label>
                        <input type="number" class="form-control" name="novoStanje" id="stanja-podstanice-za-racun-novoStanje"
                            :class="{'valid': !$v.stanjaPodstaniceZaRacun.novoStanje.$invalid, 'invalid': $v.stanjaPodstaniceZaRacun.novoStanje.$invalid }" v-model.number="$v.stanjaPodstaniceZaRacun.novoStanje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.ukupnaPovrsina')" for="stanja-podstanice-za-racun-ukupnaPovrsina">Ukupna Povrsina</label>
                        <input type="number" class="form-control" name="ukupnaPovrsina" id="stanja-podstanice-za-racun-ukupnaPovrsina"
                            :class="{'valid': !$v.stanjaPodstaniceZaRacun.ukupnaPovrsina.$invalid, 'invalid': $v.stanjaPodstaniceZaRacun.ukupnaPovrsina.$invalid }" v-model.number="$v.stanjaPodstaniceZaRacun.ukupnaPovrsina.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.utrosakPoM2')" for="stanja-podstanice-za-racun-utrosakPoM2">Utrosak Po M 2</label>
                        <input type="number" class="form-control" name="utrosakPoM2" id="stanja-podstanice-za-racun-utrosakPoM2"
                            :class="{'valid': !$v.stanjaPodstaniceZaRacun.utrosakPoM2.$invalid, 'invalid': $v.stanjaPodstaniceZaRacun.utrosakPoM2.$invalid }" v-model.number="$v.stanjaPodstaniceZaRacun.utrosakPoM2.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stanjaPodstaniceZaRacun.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stanja-podstanice-za-racun-update.component.ts">
</script>
