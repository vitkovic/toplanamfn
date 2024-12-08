<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.vrstaTransakcije.home.title')" id="vrsta-transakcije-heading">Vrsta Transakcijes</span>
            <router-link :to="{name: 'VrstaTransakcijeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-vrsta-transakcije">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.vrstaTransakcije.home.createLabel')">
                    Create a new Vrsta Transakcije
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
        <div class="alert alert-warning" v-if="!isFetching && vrstaTransakcijes && vrstaTransakcijes.length === 0">
            <span v-text="$t('toplanaApp.vrstaTransakcije.home.notFound')">No vrstaTransakcijes found</span>
        </div>
        <div class="table-responsive" v-if="vrstaTransakcijes && vrstaTransakcijes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.vrstaTransakcije.vrsta')">Vrsta</span></th>
                    <th><span v-text="$t('toplanaApp.vrstaTransakcije.dodajeVrednost')">Dodaje Vrednost</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="vrstaTransakcije in vrstaTransakcijes"
                    :key="vrstaTransakcije.id">
                    <td>
                        <router-link :to="{name: 'VrstaTransakcijeView', params: {vrstaTransakcijeId: vrstaTransakcije.id}}">{{vrstaTransakcije.id}}</router-link>
                    </td>
                    <td>{{vrstaTransakcije.vrsta}}</td>
                    <td>{{vrstaTransakcije.dodajeVrednost}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'VrstaTransakcijeView', params: {vrstaTransakcijeId: vrstaTransakcije.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'VrstaTransakcijeEdit', params: {vrstaTransakcijeId: vrstaTransakcije.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(vrstaTransakcije)"
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
            <span slot="modal-title"><span id="toplanaApp.vrstaTransakcije.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-vrstaTransakcije-heading" v-text="$t('toplanaApp.vrstaTransakcije.delete.question', {'id': removeId})">Are you sure you want to delete this Vrsta Transakcije?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-vrstaTransakcije" v-text="$t('entity.action.delete')" v-on:click="removeVrstaTransakcije()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./vrsta-transakcije.component.ts">
</script>
