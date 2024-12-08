<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stanjaPodstanice.home.title')" id="stanja-podstanice-heading">Stanja Podstanices</span>
            <router-link :to="{name: 'StanjaPodstaniceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stanja-podstanice">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stanjaPodstanice.home.createLabel')">
                    Create a new Stanja Podstanice
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
        <div class="alert alert-warning" v-if="!isFetching && stanjaPodstanices && stanjaPodstanices.length === 0">
            <span v-text="$t('toplanaApp.stanjaPodstanice.home.notFound')">No stanjaPodstanices found</span>
        </div>
        <div class="table-responsive" v-if="stanjaPodstanices && stanjaPodstanices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stanje')"><span v-text="$t('toplanaApp.stanjaPodstanice.stanje')">Stanje</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stanje'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumOcitavanja')"><span v-text="$t('toplanaApp.stanjaPodstanice.datumOcitavanja')">Datum Ocitavanja</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumOcitavanja'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('podstanica.id')"><span v-text="$t('toplanaApp.stanjaPodstanice.podstanica')">Podstanica</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'podstanica.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stanjaPodstanice in stanjaPodstanices"
                    :key="stanjaPodstanice.id">
                    <td>
                        <router-link :to="{name: 'StanjaPodstaniceView', params: {stanjaPodstaniceId: stanjaPodstanice.id}}">{{stanjaPodstanice.id}}</router-link>
                    </td>
                    <td>{{stanjaPodstanice.stanje}}</td>
                    <td>{{stanjaPodstanice.datumOcitavanja ? $d(Date.parse(stanjaPodstanice.datumOcitavanja), 'short') : ''}}</td>
                    <td>
                        <div v-if="stanjaPodstanice.podstanica">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: stanjaPodstanice.podstanica.id}}">{{stanjaPodstanice.podstanica.broj}} </router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StanjaPodstaniceView', params: {stanjaPodstaniceId: stanjaPodstanice.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StanjaPodstaniceEdit', params: {stanjaPodstaniceId: stanjaPodstanice.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stanjaPodstanice)"
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
            <span slot="modal-title"><span id="toplanaApp.stanjaPodstanice.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stanjaPodstanice-heading" v-text="$t('toplanaApp.stanjaPodstanice.delete.question', {'id': removeId})">Are you sure you want to delete this Stanja Podstanice?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stanjaPodstanice" v-text="$t('entity.action.delete')" v-on:click="removeStanjaPodstanice()">Delete</button>
            </div>
        </b-modal>
        <div v-show="stanjaPodstanices && stanjaPodstanices.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stanja-podstanice.component.ts">
</script>
