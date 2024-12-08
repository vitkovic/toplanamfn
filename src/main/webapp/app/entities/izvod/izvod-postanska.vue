<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.izvod.home.titlePostanska')" id="izvod-heading">Izvods</span>
            <router-link :to="{name: 'IzvodPostanskaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-izvod">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.izvod.home.createLabelPostanska')">
                    Create a new Izvod
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
        <div class="alert alert-warning" v-if="!isFetching && izvods && izvods.length === 0">
            <span v-text="$t('toplanaApp.izvod.home.postanskaNotFound')">No izvods found</span>
        </div>
        <div class="table-responsive" v-if="izvods && izvods.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datumIzvodaZaglavlje')"><span v-text="$t('toplanaApp.izvod.datum')">Datum</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datum'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('brojIzvoda')"><span v-text="$t('toplanaApp.izvod.broj')">Broj</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'broj'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="izvod in izvods"
                    :key="izvod.id">
                    <td>
                        <router-link :to="{name: 'IzvodView', params: {izvodId: izvod.id}}">{{izvod.id}}</router-link>
                    </td>
                    <td>{{izvod.datumIzvodaZaglavlje ? $d(Date.parse(izvod.datumIzvodaZaglavlje), 'short') : ''}}</td>
                    <td>{{izvod.brojIzvoda}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'IzvodPostanskaView', params: {izvodId: izvod.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <!--
                            <router-link :to="{name: 'IzvodEdit', params: {izvodId: izvod.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            -->
                            <b-button v-on:click="prepareRemove(izvod)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="toplanaApp.izvod.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-izvod-heading" v-text="$t('toplanaApp.izvod.delete.question', {'id': removeId})">Are you sure you want to delete this Izvod?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-izvod" v-text="$t('entity.action.delete')" v-on:click="removeIzvod()">Delete</button>
            </div>
        </b-modal>
        <div v-show="izvods && izvods.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./izvod-postanska.component.ts">
</script>
