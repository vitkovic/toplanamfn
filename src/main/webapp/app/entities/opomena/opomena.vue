<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.opomena.home.title')" id="opomena-heading">Opomenas</span>
            <router-link :to="{name: 'OpomenaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-opomena">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.opomena.home.createLabel')">
                    Create a new Opomena
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
        <div class="alert alert-warning" v-if="!isFetching && opomenas && opomenas.length === 0">
            <span v-text="$t('toplanaApp.opomena.home.notFound')">No opomenas found</span>
        </div>
        <div class="table-responsive" v-if="opomenas && opomenas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumOpomene')"><span v-text="$t('toplanaApp.opomena.datumOpomene')">Datum Opomene</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumOpomene'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('zakljucniDatum')"><span v-text="$t('toplanaApp.opomena.zakljucniDatum')">Zakljucni Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'zakljucniDatum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('iznos')"><span v-text="$t('toplanaApp.opomena.iznos')">Iznos</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'iznos'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumIzmirenja')"><span v-text="$t('toplanaApp.opomena.datumIzmirenja')">Datum Izmirenja</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumIzmirenja'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan.id')"><span v-text="$t('toplanaApp.opomena.stan')">Stan</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="opomena in opomenas"
                    :key="opomena.id">
                    <td>
                        <router-link :to="{name: 'OpomenaView', params: {opomenaId: opomena.id}}">{{opomena.id}}</router-link>
                    </td>
                    <td>{{opomena.datumOpomene}}</td>
                    <td>{{opomena.zakljucniDatum}}</td>
                    <td>{{opomena.iznos}}</td>
                    <td>{{opomena.datumIzmirenja}}</td>
                    <td>
                        <div v-if="opomena.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: opomena.stan.id}}">{{opomena.stan.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'OpomenaView', params: {opomenaId: opomena.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OpomenaEdit', params: {opomenaId: opomena.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(opomena)"
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
            <span slot="modal-title"><span id="toplanaApp.opomena.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-opomena-heading" v-text="$t('toplanaApp.opomena.delete.question', {'id': removeId})">Are you sure you want to delete this Opomena?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-opomena" v-text="$t('entity.action.delete')" v-on:click="removeOpomena()">Delete</button>
            </div>
        </b-modal>
        <div v-show="opomenas && opomenas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./opomena.component.ts">
</script>
