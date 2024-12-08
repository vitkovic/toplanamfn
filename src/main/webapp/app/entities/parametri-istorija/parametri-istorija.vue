<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.parametriIstorija.home.title')" id="parametri-istorija-heading">Parametri Istorijas</span>
            <router-link :to="{name: 'ParametriIstorijaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-parametri-istorija">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.parametriIstorija.home.createLabel')">
                    Create a new Parametri Istorija
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
        <div class="alert alert-warning" v-if="!isFetching && parametriIstorijas && parametriIstorijas.length === 0">
            <span v-text="$t('toplanaApp.parametriIstorija.home.notFound')">No parametriIstorijas found</span>
        </div>
        <div class="table-responsive" v-if="parametriIstorijas && parametriIstorijas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.parametriIstorija.vrednost')">Vrednost</span></th>
                    <th><span v-text="$t('toplanaApp.parametriIstorija.vaziOd')">Vazi Od</span></th>
                    <th><span v-text="$t('toplanaApp.parametriIstorija.vaziDo')">Vazi Do</span></th>
                    <th><span v-text="$t('toplanaApp.parametriIstorija.parametri')">Parametri</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="parametriIstorija in parametriIstorijas"
                    :key="parametriIstorija.id">
                    <td>
                        <router-link :to="{name: 'ParametriIstorijaView', params: {parametriIstorijaId: parametriIstorija.id}}">{{parametriIstorija.id}}</router-link>
                    </td>
                    <td>{{parametriIstorija.vrednost}}</td>
                    <td>{{parametriIstorija.vaziOd}}</td>
                    <td>{{parametriIstorija.vaziDo}}</td>
                    <td>
                        <div v-if="parametriIstorija.parametri">
                            <router-link :to="{name: 'ParametriView', params: {parametriId: parametriIstorija.parametri.id}}">{{parametriIstorija.parametri.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ParametriIstorijaView', params: {parametriIstorijaId: parametriIstorija.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ParametriIstorijaEdit', params: {parametriIstorijaId: parametriIstorija.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(parametriIstorija)"
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
            <span slot="modal-title"><span id="toplanaApp.parametriIstorija.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-parametriIstorija-heading" v-text="$t('toplanaApp.parametriIstorija.delete.question', {'id': removeId})">Are you sure you want to delete this Parametri Istorija?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-parametriIstorija" v-text="$t('entity.action.delete')" v-on:click="removeParametriIstorija()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./parametri-istorija.component.ts">
</script>
