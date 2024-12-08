<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.sifraPromene.home.title')" id="sifra-promene-heading">Sifra Promenes</span>
            <router-link :to="{name: 'SifraPromeneCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sifra-promene">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.sifraPromene.home.createLabel')">
                    Create a new Sifra Promene
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
        <div class="alert alert-warning" v-if="!isFetching && sifraPromenes && sifraPromenes.length === 0">
            <span v-text="$t('toplanaApp.sifraPromene.home.notFound')">No sifraPromenes found</span>
        </div>
        <div class="table-responsive" v-if="sifraPromenes && sifraPromenes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.sifraPromene.broj')"></span></th>
                    <th><span v-text="$t('toplanaApp.sifraPromene.sifra')">Sifra</span></th>
                    <th><span v-text="$t('toplanaApp.sifraPromene.primenjujeSeKod')"></span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sifraPromene in sifraPromenes"
                    :key="sifraPromene.id">
                    <td>
                        <router-link :to="{name: 'SifraPromeneView', params: {sifraPromeneId: sifraPromene.id}}">{{sifraPromene.id}}</router-link>
                    </td>
                    <td>{{sifraPromene.broj}}</td>
                    <td>{{sifraPromene.sifra}}</td>
                    <td>{{sifraPromene.tipPromene}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SifraPromeneView', params: {sifraPromeneId: sifraPromene.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SifraPromeneEdit', params: {sifraPromeneId: sifraPromene.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sifraPromene)"
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
            <span slot="modal-title"><span id="toplanaApp.sifraPromene.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sifraPromene-heading" v-text="$t('toplanaApp.sifraPromene.delete.question', {'id': removeId})">Are you sure you want to delete this Sifra Promene?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sifraPromene" v-text="$t('entity.action.delete')" v-on:click="removeSifraPromene()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sifra-promene.component.ts">
</script>
