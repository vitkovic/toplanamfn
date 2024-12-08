<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.cene.home.title')" id="cene-heading">Cenes</span>
            <router-link :to="{name: 'CeneCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cene">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.cene.home.createLabel')">
                    Create a new Cene
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
        <div class="alert alert-warning" v-if="!isFetching && cenes && cenes.length === 0">
            <span v-text="$t('toplanaApp.cene.home.notFound')">No cenes found</span>
        </div>
        <div class="table-responsive" v-if="cenes && cenes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.cene.kwh')">Kwh</span></th>
                    <th><span v-text="$t('toplanaApp.cene.fix')">Fix</span></th>
                    <th><span v-text="$t('toplanaApp.cene.odrzavanje')">Odrzavanje</span></th>
                    <th><span v-text="$t('toplanaApp.cene.ostalo')">Ostalo</span></th>
                    <th><span v-text="$t('toplanaApp.cene.pdv1')">Ostalo</span></th>
                    <th><span v-text="$t('toplanaApp.cene.pdv2')">Ostalo</span></th>
                    <th><span v-text="$t('toplanaApp.cene.popust')">Ostalo</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cene in cenes"
                    :key="cene.id">
                    <td>
                        <router-link :to="{name: 'CeneView', params: {ceneId: cene.id}}">{{cene.id}}</router-link>
                    </td>
                    <td>{{cene.kwh}}</td>
                    <td>{{cene.fix}}</td>
                    <td>{{cene.odrzavanje}}</td>
                    <td>{{cene.ostalo}}</td>
                    <td>{{cene.pdv1}}%</td>
                    <td>{{cene.pdv2}}%</td>
                    <td>{{cene.popust1}}%</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CeneView', params: {ceneId: cene.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CeneEdit', params: {ceneId: cene.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cene)"
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
            <span slot="modal-title"><span id="toplanaApp.cene.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cene-heading" v-text="$t('toplanaApp.cene.delete.question', {'id': removeId})">Are you sure you want to delete this Cene?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cene" v-text="$t('entity.action.delete')" v-on:click="removeCene()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./cene.component.ts">
</script>
