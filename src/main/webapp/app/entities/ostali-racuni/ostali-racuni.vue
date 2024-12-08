<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.ostaliRacuni.home.title')" id="opomena-heading">Opomenas</span>
            <router-link :to="{name: 'OstaliRacuniCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ostali-racuni">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.ostaliRacuni.home.createLabel')">
                    
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
        <div class="alert alert-warning" v-if="!isFetching && ostaliRacunis && ostaliRacunis.length === 0">
            <span v-text="$t('toplanaApp.ostaliRacuni.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="ostaliRacunis && ostaliRacunis.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('naziv')"><span v-text="$t('toplanaApp.ostaliRacuni.naziv')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'naziv'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sifra')"><span v-text="$t('toplanaApp.ostaliRacuni.sifra')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sifra'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('aktivan')"><span v-text="$t('toplanaApp.ostaliRacuni.aktivan')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aktivan'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan')"><span v-text="$t('toplanaApp.ostaliRacuni.stan')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="ostaliRacuni in ostaliRacunis"
                    :key="ostaliRacuni.id">
                    <td>
                        <router-link :to="{name: 'OstaliRacuniView', params: {ostaliRacuniId: ostaliRacuni.id}}">{{ostaliRacuni.id}}</router-link>
                    </td>
                    <td>{{ostaliRacuni.naziv}}</td>
                    <td>{{ostaliRacuni.sifra}}</td>
                    <td>{{ostaliRacuni.aktivan}}</td>
                    <td>
                        <div v-if="ostaliRacuni.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: ostaliRacuni.stan.id}}">{{ostaliRacuni.stan.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'OstaliRacuniView', params: {ostaliRacuniId: ostaliRacuni.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OstaliRacuniEdit', params: {ostaliRacuniId: ostaliRacuni.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(ostaliRacuni)"
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
            <span slot="modal-title"><span id="toplanaApp.ostaliRacuni.delete.question" v-text="$t('entity.delete.title')"></span></span>
            <div class="modal-body">
                <p id="jhi-delete-ostaliRacuni-heading" v-text="$t('toplanaApp.ostaliRacuni.delete.question', {'id': removeId})"></p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-ostaliRacuni-opomena" v-text="$t('entity.action.delete')" v-on:click="removeOstaliRacuni()">Delete</button>
            </div>
        </b-modal>
        <div v-show="ostaliRacunis && ostaliRacunis.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ostali-racuni.component.ts">
</script>
