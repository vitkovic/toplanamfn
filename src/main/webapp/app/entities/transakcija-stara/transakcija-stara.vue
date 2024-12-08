<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.transakcijaStara.home.title')" id="transakcija-stara-heading">Transakcija Staras</span>
            <router-link :to="{name: 'TransakcijaStaraCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-transakcija-stara">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.transakcijaStara.home.createLabel')">
                    Create a new Transakcija Stara
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && transakcijaStaras && transakcijaStaras.length === 0">
            <span v-text="$t('toplanaApp.transakcijaStara.home.notFound')">No transakcijaStaras found</span>
        </div>
        <div class="table-responsive" v-if="transakcijaStaras && transakcijaStaras.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datum')"><span v-text="$t('toplanaApp.transakcijaStara.datum')">Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('valuta')"><span v-text="$t('toplanaApp.transakcijaStara.valuta')">Valuta</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valuta'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('toplanaApp.transakcijaStara.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('opis')"><span v-text="$t('toplanaApp.transakcijaStara.opis')">Opis</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'opis'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('duguje')"><span v-text="$t('toplanaApp.transakcijaStara.duguje')">Duguje</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duguje'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('potrazuje')"><span v-text="$t('toplanaApp.transakcijaStara.potrazuje')">Potrazuje</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'potrazuje'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vrstaTransakcije.id')"><span v-text="$t('toplanaApp.transakcijaStara.vrstaTransakcije')">Vrsta Transakcije</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vrstaTransakcije.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifraDokumenta.id')"><span v-text="$t('toplanaApp.transakcijaStara.sifraDokumenta')">Sifra Dokumenta</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifraDokumenta.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifraPromene.id')"><span v-text="$t('toplanaApp.transakcijaStara.sifraPromene')">Sifra Promene</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifraPromene.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('cene.id')"><span v-text="$t('toplanaApp.transakcijaStara.cene')">Cene</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cene.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan.id')"><span v-text="$t('toplanaApp.transakcijaStara.stan')">Stan</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="transakcijaStara in transakcijaStaras"
                    :key="transakcijaStara.id">
                    <td>
                        <router-link :to="{name: 'TransakcijaStaraView', params: {transakcijaStaraId: transakcijaStara.id}}">{{transakcijaStara.id}}</router-link>
                    </td>
                    <td>{{transakcijaStara.datum}}</td>
                    <td>{{transakcijaStara.valuta}}</td>
                    <td>{{transakcijaStara.status}}</td>
                    <td>{{transakcijaStara.opis}}</td>
                    <td>{{transakcijaStara.duguje}}</td>
                    <td>{{transakcijaStara.potrazuje}}</td>
                    <td>
                        <div v-if="transakcijaStara.vrstaTransakcije">
                            <router-link :to="{name: 'VrstaTransakcijeView', params: {vrstaTransakcijeId: transakcijaStara.vrstaTransakcije.id}}">{{transakcijaStara.vrstaTransakcije.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="transakcijaStara.sifraDokumenta">
                            <router-link :to="{name: 'SifraDokumentaView', params: {sifraDokumentaId: transakcijaStara.sifraDokumenta.id}}">{{transakcijaStara.sifraDokumenta.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="transakcijaStara.sifraPromene">
                            <router-link :to="{name: 'SifraPromeneView', params: {sifraPromeneId: transakcijaStara.sifraPromene.id}}">{{transakcijaStara.sifraPromene.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="transakcijaStara.cene">
                            <router-link :to="{name: 'CeneStareView', params: {ceneStareId: transakcijaStara.cene.id}}">{{transakcijaStara.cene.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="transakcijaStara.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: transakcijaStara.stan.id}}">{{transakcijaStara.stan.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TransakcijaStaraView', params: {transakcijaStaraId: transakcijaStara.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TransakcijaStaraEdit', params: {transakcijaStaraId: transakcijaStara.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(transakcijaStara)"
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
            <span slot="modal-title"><span id="toplanaApp.transakcijaStara.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-transakcijaStara-heading" v-text="$t('toplanaApp.transakcijaStara.delete.question', {'id': removeId})">Are you sure you want to delete this Transakcija Stara?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-transakcijaStara" v-text="$t('entity.action.delete')" v-on:click="removeTransakcijaStara()">Delete</button>
            </div>
        </b-modal>
        <div v-show="transakcijaStaras && transakcijaStaras.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./transakcija-stara.component.ts">
</script>
