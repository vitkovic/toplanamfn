<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stavkeIzvoda.home.title')" id="stavke-izvoda-heading">Stavke Izvodas</span>
            <!--
            <router-link :to="{name: 'StavkeIzvodaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stavke-izvoda">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stavkeIzvoda.home.createLabel')">
                    Create a new Stavke Izvoda
                </span>
            </router-link>
            -->
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && stavkeIzvodas && stavkeIzvodas.length === 0">
            <span v-text="$t('toplanaApp.stavkeIzvoda.home.notFound')">No stavkeIzvodas found</span>
        </div>
        <div class="table-responsive" v-if="stavkeIzvodas && stavkeIzvodas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('broj')"><span v-text="$t('toplanaApp.izvod.rbr')">Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('valuta')"><span v-text="$t('toplanaApp.stavkeIzvoda.valuta')">Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rasporedjena')"><span v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')">Rasporedjena</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rasporedjena'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('toplanaApp.stavkeIzvoda.brojTekucegRacuna')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifra')"><span v-text="$t('toplanaApp.stavkeIzvoda.brojPartijeKodPoverioca')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifra'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('opis')"><span v-text="$t('toplanaApp.stavkeIzvoda.imeVlasnikaTekucegRacuna')">Opis</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'opis'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adresa')"><span v-text="$t('toplanaApp.stavkeIzvoda.adresaVlasnika')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'opis'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('iznos')"><span v-text="$t('toplanaApp.stavkeIzvoda.iznos')">Iznos</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'iznos'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('transakcija.id')"><span v-text="$t('toplanaApp.stavkeIzvoda.transakcija')">Transakcija</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transakcija.id'"></jhi-sort-indicator></th>
                    
                    <!--
                    <th v-on:click="changeOrder('transakcijaStara.id')"><span v-text="$t('toplanaApp.stavkeIzvoda.transakcijaStara')">Transakcija Stara</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transakcijaStara.id'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('izvod.id')"><span v-text="$t('toplanaApp.stavkeIzvoda.izvod')">Izvod</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'izvod.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stavkeIzvoda in stavkeIzvodas"
                    :key="stavkeIzvoda.id">
                    <td>{{stavkeIzvoda.broj}}</td>
                    <td>{{stavkeIzvoda.valuta}}</td>                    
                    <td>{{stavkeIzvoda.rasporedjena}}</td>
                    <td>{{stavkeIzvoda.brojTekucegRacuna}}</td>
                    <td>{{stavkeIzvoda.brojPartijePoverioc}}</td>
                    <td>{{stavkeIzvoda.ime}}</td>
                    <td>{{stavkeIzvoda.adresa}}</td>
                    <td>{{stavkeIzvoda.iznos | currency('')}}</td>
                    <td>
                        <div v-if="stavkeIzvoda.transakcija">
                            <router-link :to="{name: 'TransakcijaView', params: {transakcijaId: stavkeIzvoda.transakcija.id}}">{{stavkeIzvoda.transakcija.id}}</router-link>
                        </div>
                    </td>                    
                    <td>
                        <div v-if="stavkeIzvoda.izvod">
                            <router-link :to="{name: 'IzvodPostanskaView', params: {izvodId: stavkeIzvoda.izvod.id}}">{{stavkeIzvoda.izvod.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StavkeIzvodaView', params: {stavkeIzvodaId: stavkeIzvoda.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StavkeIzvodaEdit', params: {stavkeIzvodaId: stavkeIzvoda.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stavkeIzvoda)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="toplanaApp.stavkeIzvoda.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stavkeIzvoda-heading" v-text="$t('toplanaApp.stavkeIzvoda.delete.question', {'id': removeId})">Are you sure you want to delete this Stavke Izvoda?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stavkeIzvoda" v-text="$t('entity.action.delete')" v-on:click="removeStavkeIzvoda()">Delete</button>
            </div>
        </b-modal>
        <div v-show="stavkeIzvodas && stavkeIzvodas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stavke-izvoda-postanska.component.ts">
</script>
