<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.home.title')" id="stanja-podstanice-za-racun-heading">Stanja Podstanice Za Racuns</span>
            <router-link :to="{name: 'StanjaPodstaniceZaRacunCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stanja-podstanice-za-racun">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.home.createLabel')">
                    Create a new Stanja Podstanice Za Racun
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
        <div class="alert alert-warning" v-if="!isFetching && stanjaPodstaniceZaRacuns && stanjaPodstaniceZaRacuns.length === 0">
            <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.home.notFound')">No stanjaPodstaniceZaRacuns found</span>
        </div>
        <div class="table-responsive" v-if="stanjaPodstaniceZaRacuns && stanjaPodstaniceZaRacuns.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.staroStanje')">Staro Stanje</span></th>
                    <th><span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.novoStanje')">Novo Stanje</span></th>
                    <th><span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.ukupnaPovrsina')">Ukupna Povrsina</span></th>
                    <th><span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.utrosakPoM2')">Utrosak Po M 2</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stanjaPodstaniceZaRacun in stanjaPodstaniceZaRacuns"
                    :key="stanjaPodstaniceZaRacun.id">
                    <td>
                        <router-link :to="{name: 'StanjaPodstaniceZaRacunView', params: {stanjaPodstaniceZaRacunId: stanjaPodstaniceZaRacun.id}}">{{stanjaPodstaniceZaRacun.id}}</router-link>
                    </td>
                    <td>{{stanjaPodstaniceZaRacun.staroStanje}}</td>
                    <td>{{stanjaPodstaniceZaRacun.novoStanje}}</td>
                    <td>{{stanjaPodstaniceZaRacun.ukupnaPovrsina}}</td>
                    <td>{{stanjaPodstaniceZaRacun.utrosakPoM2}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StanjaPodstaniceZaRacunView', params: {stanjaPodstaniceZaRacunId: stanjaPodstaniceZaRacun.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StanjaPodstaniceZaRacunEdit', params: {stanjaPodstaniceZaRacunId: stanjaPodstaniceZaRacun.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stanjaPodstaniceZaRacun)"
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
            <span slot="modal-title"><span id="toplanaApp.stanjaPodstaniceZaRacun.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stanjaPodstaniceZaRacun-heading" v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.delete.question', {'id': removeId})">Are you sure you want to delete this Stanja Podstanice Za Racun?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stanjaPodstaniceZaRacun" v-text="$t('entity.action.delete')" v-on:click="removeStanjaPodstaniceZaRacun()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./stanja-podstanice-za-racun.component.ts">
</script>
