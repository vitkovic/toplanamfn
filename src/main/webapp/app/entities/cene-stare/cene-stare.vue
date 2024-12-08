<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.ceneStare.home.title')" id="cene-stare-heading">Cene Stares</span>
            <router-link :to="{name: 'CeneStareCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cene-stare">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.ceneStare.home.createLabel')">
                    Create a new Cene Stare
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
        <div class="alert alert-warning" v-if="!isFetching && ceneStares && ceneStares.length === 0">
            <span v-text="$t('toplanaApp.ceneStare.home.notFound')">No ceneStares found</span>
        </div>
        <div class="table-responsive" v-if="ceneStares && ceneStares.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.ceneStare.kwh')">Kwh</span></th>
                    <th><span v-text="$t('toplanaApp.ceneStare.fix')">Fix</span></th>
                    <th><span v-text="$t('toplanaApp.ceneStare.odrzavanje')">Odrzavanje</span></th>
                    <th><span v-text="$t('toplanaApp.ceneStare.ostalo')">Ostalo</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="ceneStare in ceneStares"
                    :key="ceneStare.id">
                    <td>
                        <router-link :to="{name: 'CeneStareView', params: {ceneStareId: ceneStare.id}}">{{ceneStare.id}}</router-link>
                    </td>
                    <td>{{ceneStare.kwh}}</td>
                    <td>{{ceneStare.fix}}</td>
                    <td>{{ceneStare.odrzavanje}}</td>
                    <td>{{ceneStare.ostalo}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CeneStareView', params: {ceneStareId: ceneStare.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CeneStareEdit', params: {ceneStareId: ceneStare.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(ceneStare)"
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
            <span slot="modal-title"><span id="toplanaApp.ceneStare.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-ceneStare-heading" v-text="$t('toplanaApp.ceneStare.delete.question', {'id': removeId})">Are you sure you want to delete this Cene Stare?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-ceneStare" v-text="$t('entity.action.delete')" v-on:click="removeCeneStare()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./cene-stare.component.ts">
</script>
