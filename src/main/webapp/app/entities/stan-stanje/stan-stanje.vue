<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stanStanje.home.title')" id="stan-stanje-heading">Stan Stanjes</span>
            <router-link :to="{name: 'StanStanjeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stan-stanje">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stanStanje.home.createLabel')">
                    Create a new Stan Stanje
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
        <div class="alert alert-warning" v-if="!isFetching && stanStanjes && stanStanjes.length === 0">
            <span v-text="$t('toplanaApp.stanStanje.home.notFound')">No stanStanjes found</span>
        </div>
        <div class="table-responsive" v-if="stanStanjes && stanStanjes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifra')"><span v-text="$t('toplanaApp.stanStanje.sifra')">Sifra</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifra'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datum')"><span v-text="$t('toplanaApp.stanStanje.datum')">Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vrednost')"><span v-text="$t('toplanaApp.stanStanje.vrednost')">Vrednost</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vrednost'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan_id')"><span v-text="$t('toplanaApp.stanStanje.stan_id')">Stan Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan_id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan.id')"><span v-text="$t('toplanaApp.stanStanje.stan')">Stan</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stanStanje in stanStanjes"
                    :key="stanStanje.id">
                    <td>
                        <router-link :to="{name: 'StanStanjeView', params: {stanStanjeId: stanStanje.id}}">{{stanStanje.id}}</router-link>
                    </td>
                    <td>{{stanStanje.sifra}}</td>
                    <td>{{stanStanje.datum}}</td>
                    <td>{{stanStanje.vrednost}}</td>
                    <td>{{stanStanje.stan_id}}</td>
                    <td>
                        <div v-if="stanStanje.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: stanStanje.stan.id}}">{{stanStanje.stan.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StanStanjeView', params: {stanStanjeId: stanStanje.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StanStanjeEdit', params: {stanStanjeId: stanStanje.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stanStanje)"
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
            <span slot="modal-title"><span id="toplanaApp.stanStanje.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stanStanje-heading" v-text="$t('toplanaApp.stanStanje.delete.question', {'id': removeId})">Are you sure you want to delete this Stan Stanje?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stanStanje" v-text="$t('entity.action.delete')" v-on:click="removeStanStanje()">Delete</button>
            </div>
        </b-modal>
        <div v-show="stanStanjes && stanStanjes.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stan-stanje.component.ts">
</script>
