<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.ugovorRate.home.title')" id="ugovor-rate-heading">Ugovor Rates</span>
            <router-link :to="{name: 'UgovorRateCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ugovor-rate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.ugovorRate.home.createLabel')">
                    Create a new Ugovor Rate
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
        <div class="alert alert-warning" v-if="!isFetching && ugovorRates && ugovorRates.length === 0">
            <span v-text="$t('toplanaApp.ugovorRate.home.notFound')">No ugovorRates found</span>
        </div>
        <div class="table-responsive" v-if="ugovorRates && ugovorRates.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('brojRata')"><span v-text="$t('toplanaApp.ugovorRate.brojRata')">Broj Rata</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brojRata'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumSklapanja')"><span v-text="$t('toplanaApp.ugovorRate.datumSklapanja')">Datum Sklapanja</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumSklapanja'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumDospeca')"><span v-text="$t('toplanaApp.ugovorRate.datumDospeca')">Datum Dospeca</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumDospeca'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan.id')"><span v-text="$t('toplanaApp.ugovorRate.stan')">Stan</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="ugovorRate in ugovorRates"
                    :key="ugovorRate.id">
                    <td>
                        <router-link :to="{name: 'UgovorRateView', params: {ugovorRateId: ugovorRate.id}}">{{ugovorRate.id}}</router-link>
                    </td>
                    <td>{{ugovorRate.brojRata}}</td>
                    <td>{{ugovorRate.datumSklapanja}}</td>
                    <td>{{ugovorRate.datumDospeca}}</td>
                    <td>
                        <div v-if="ugovorRate.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: ugovorRate.stan.id}}">{{ugovorRate.stan.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'UgovorRateView', params: {ugovorRateId: ugovorRate.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UgovorRateEdit', params: {ugovorRateId: ugovorRate.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(ugovorRate)"
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
            <span slot="modal-title"><span id="toplanaApp.ugovorRate.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-ugovorRate-heading" v-text="$t('toplanaApp.ugovorRate.delete.question', {'id': removeId})">Are you sure you want to delete this Ugovor Rate?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-ugovorRate" v-text="$t('entity.action.delete')" v-on:click="removeUgovorRate()">Delete</button>
            </div>
        </b-modal>
        <div v-show="ugovorRates && ugovorRates.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ugovor-rate.component.ts">
</script>
