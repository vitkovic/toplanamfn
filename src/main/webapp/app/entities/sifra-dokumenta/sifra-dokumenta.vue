<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.sifraDokumenta.home.title')" id="sifra-dokumenta-heading">Sifra Dokumentas</span>
            <router-link :to="{name: 'SifraDokumentaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sifra-dokumenta">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.sifraDokumenta.home.createLabel')">
                    Create a new Sifra Dokumenta
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
        <div class="alert alert-warning" v-if="!isFetching && sifraDokumentas && sifraDokumentas.length === 0">
            <span v-text="$t('toplanaApp.sifraDokumenta.home.notFound')">No sifraDokumentas found</span>
        </div>
        <div class="table-responsive" v-if="sifraDokumentas && sifraDokumentas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.sifraDokumenta.sifra')">Sifra</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sifraDokumenta in sifraDokumentas"
                    :key="sifraDokumenta.id">
                    <td>
                        <router-link :to="{name: 'SifraDokumentaView', params: {sifraDokumentaId: sifraDokumenta.id}}">{{sifraDokumenta.id}}</router-link>
                    </td>
                    <td>{{sifraDokumenta.sifra}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SifraDokumentaView', params: {sifraDokumentaId: sifraDokumenta.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SifraDokumentaEdit', params: {sifraDokumentaId: sifraDokumenta.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sifraDokumenta)"
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
            <span slot="modal-title"><span id="toplanaApp.sifraDokumenta.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sifraDokumenta-heading" v-text="$t('toplanaApp.sifraDokumenta.delete.question', {'id': removeId})">Are you sure you want to delete this Sifra Dokumenta?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sifraDokumenta" v-text="$t('entity.action.delete')" v-on:click="removeSifraDokumenta()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sifra-dokumenta.component.ts">
</script>
