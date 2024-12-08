<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stan.home.title')" id="stan-heading">Stans</span>
            <router-link :to="{name: 'StanCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stan">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stan.home.createLabel')">
                    Create a new Stan
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
        <div class="alert alert-warning" v-if="!isFetching && stans && stans.length === 0">
            <span v-text="$t('toplanaApp.stan.home.notFound')">No stans found</span>
        </div>
        <div class="table-responsive" v-if="stans && stans.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifra')"><span v-text="$t('toplanaApp.stan.sifra')">Sifra</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifra'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('povrsina')"><span v-text="$t('toplanaApp.stan.povrsina')">Povrsina</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'povrsina'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('ulaz')"><span v-text="$t('toplanaApp.stan.ulaz')">Ulaz</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ulaz'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('broj')"><span v-text="$t('toplanaApp.stan.broj')">Broj</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'broj'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ukljucen')"><span v-text="$t('toplanaApp.stan.ukljucen')">Ukljucen</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ukljucen'"></jhi-sort-indicator></th>
                    
                    
                    <th v-on:click="changeOrder('tipPotrosaca.id')"><span v-text="$t('toplanaApp.stan.tipPotrosaca')">Tip Potrosaca</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipPotrosaca.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('podstanica.id')"><span v-text="$t('toplanaApp.stan.podstanica')">Podstanica</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'podstanica.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vlasnik.id')"><span v-text="$t('toplanaApp.stan.vlasnik')">Vlasnik</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vlasnik.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stan in stans"
                    :key="stan.id">
                    <td>
                        <router-link :to="{name: 'StanView', params: {stanId: stan.id}}">{{stan.id}}</router-link>
                    </td>
                    <td>{{stan.sifra}}</td>                    
                    <td>{{stan.povrsina}}</td>                    
                    <td>{{stan.ulaz}}</td>
                    <td>{{stan.broj}}</td>
                    <td>{{ stan.ukljucen ? 'Да' : 'Не' }}</td>                    
                    
                    
                    <td>
                        <div v-if="stan.tipPotrosaca">
                            <router-link :to="{name: 'TipPotrosacaView', params: {tipPotrosacaId: stan.tipPotrosaca.id}}">{{stan.tipPotrosaca.tip}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="stan.podstanica">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: stan.podstanica.id}}">{{stan.podstanica.broj}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="stan.vlasnik">
                            <router-link :to="{name: 'VlasnikView', params: {vlasnikId: stan.vlasnik.id}}">{{stan.vlasnik.prezime}} {{stan.vlasnik.ime}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StanView', params: {stanId: stan.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StanEdit', params: {stanId: stan.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stan)"
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
            <span slot="modal-title"><span id="toplanaApp.stan.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stan-heading" v-text="$t('toplanaApp.stan.delete.question', {'id': removeId})">Are you sure you want to delete this Stan?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stan" v-text="$t('entity.action.delete')" v-on:click="removeStan()">Delete</button>
            </div>
        </b-modal>
        <div v-show="stans && stans.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stan.component.ts">
</script>
