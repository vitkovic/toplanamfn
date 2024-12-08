<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.nacrtRacuna.home.title')" id="nacrt-racuna-heading">Nacrt Racunas</span>
            <router-link :to="{name: 'NacrtRacunaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-nacrt-racuna">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.nacrtRacuna.home.createLabel')">
                    Create a new Nacrt Racuna
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
        <div class="alert alert-warning" v-if="!isFetching && nacrtRacunas && nacrtRacunas.length === 0">
            <span v-text="$t('toplanaApp.nacrtRacuna.home.notFound')">No nacrtRacunas found</span>
        </div>
        <div class="table-responsive" v-if="nacrtRacunas && nacrtRacunas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumRacuna')"><span v-text="$t('toplanaApp.nacrtRacuna.datumRacuna')">Datum Racuna</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumRacuna'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('period')"><span v-text="$t('toplanaApp.nacrtRacuna.period')">Period</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'period'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('valutaPlacanja')"><span v-text="$t('toplanaApp.nacrtRacuna.valutaPlacanja')">Valuta Placanja</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valutaPlacanja'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="nacrtRacuna in nacrtRacunas"
                    :key="nacrtRacuna.id">
                    <td>
                        <router-link :to="{name: 'NacrtRacunaView', params: {nacrtRacunaId: nacrtRacuna.id}}">{{nacrtRacuna.id}}</router-link>
                    </td>
                    <td>{{nacrtRacuna.datumRacuna ? $d(Date.parse(nacrtRacuna.datumRacuna), 'short') : ''}}</td>
                    <td>{{nacrtRacuna.period}}</td>
                    <td>{{nacrtRacuna.datumRacuna ? $d(Date.parse(nacrtRacuna.valutaPlacanja), 'short') : ''}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'NacrtRacunaView', params: {nacrtRacunaId: nacrtRacuna.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'NacrtRacunaEdit', params: {nacrtRacunaId: nacrtRacuna.id}}"  tag="button" class="btn btn-primary btn-sm edit ml-1">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <router-link :to="{name: 'RekapitulacijaPdv', params: {nacrtRacunaId: nacrtRacuna.id}}"  tag="button" class="btn btn-info btn-sm edit ml-1">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.rekapitulacija')">rekapitulacija</span>
                            </router-link>
                            <b-button v-if="!nacrtRacuna.proknjizen" v-on:click="prepareRemove(nacrtRacuna)"
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
            <span slot="modal-title"><span id="toplanaApp.nacrtRacuna.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-nacrtRacuna-heading" v-text="$t('toplanaApp.nacrtRacuna.delete.question', {'id': removeId})">Are you sure you want to delete this Nacrt Racuna?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-nacrtRacuna" v-text="$t('entity.action.delete')" v-on:click="removeNacrtRacuna()">Delete</button>
            </div>
        </b-modal>
        <div v-show="nacrtRacunas && nacrtRacunas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./nacrt-racuna.component.ts">
</script>
