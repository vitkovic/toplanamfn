<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.parametri.home.title')" id="parametri-heading">Parametris</span>
            <router-link :to="{name: 'ParametriCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-parametri">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.parametri.home.createLabel')">
                    Create a new Parametri
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
        <div class="alert alert-warning" v-if="!isFetching && parametris && parametris.length === 0">
            <span v-text="$t('toplanaApp.parametri.home.notFound')">No parametris found</span>
        </div>
        <div class="table-responsive" v-if="parametris && parametris.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.parametri.naziv')">Naziv</span></th>
                    <th><span v-text="$t('toplanaApp.parametri.vrednost')">Vrednost</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="parametri in parametris"
                    :key="parametri.id">
                    <td>
                        <router-link :to="{name: 'ParametriView', params: {parametriId: parametri.id}}">{{parametri.id}}</router-link>
                    </td>
                    <td>{{parametri.naziv}}</td>
                    <td>{{parametri.vrednost}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ParametriView', params: {parametriId: parametri.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ParametriEdit', params: {parametriId: parametri.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(parametri)"
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
            <span slot="modal-title"><span id="toplanaApp.parametri.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-parametri-heading" v-text="$t('toplanaApp.parametri.delete.question', {'id': removeId})">Are you sure you want to delete this Parametri?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-parametri" v-text="$t('entity.action.delete')" v-on:click="removeParametri()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./parametri.component.ts">
</script>
