<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.podstanica.home.title')" id="podstanica-heading">Podstanicas</span>
            <router-link :to="{name: 'PodstanicaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-podstanica">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.podstanica.home.createLabel')">
                    Create a new Podstanica
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
        <div class="alert alert-warning" v-if="!isFetching && podstanicas && podstanicas.length === 0">
            <span v-text="$t('toplanaApp.podstanica.home.notFound')">No podstanicas found</span>
        </div>
        <div class="table-responsive" v-if="podstanicas && podstanicas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.podstanica.broj')">Naziv</span></th>
                    <th><span v-text="$t('toplanaApp.podstanica.naziv')">Naziv</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="podstanica in podstanicas"
                    :key="podstanica.id">
                    <td>
                        <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: podstanica.id}}">{{podstanica.id}}</router-link>
                    </td>
                    <td>{{podstanica.broj}}</td>
                    <td>{{podstanica.naziv}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: podstanica.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PodstanicaEdit', params: {podstanicaId: podstanica.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(podstanica)"
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
            <span slot="modal-title"><span id="toplanaApp.podstanica.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-podstanica-heading" v-text="$t('toplanaApp.podstanica.delete.question', {'id': removeId})">Are you sure you want to delete this Podstanica?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-podstanica" v-text="$t('entity.action.delete')" v-on:click="removePodstanica()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./podstanica.component.ts">
</script>
