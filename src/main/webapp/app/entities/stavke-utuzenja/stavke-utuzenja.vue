<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.stavkeUtuzenja.home.title')" id="stavke-utuzenja-heading">Stavke Utuzenjas</span>
            <router-link :to="{name: 'StavkeUtuzenjaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-stavke-utuzenja">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.stavkeUtuzenja.home.createLabel')">
                    Create a new Stavke Utuzenja
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
        <div class="alert alert-warning" v-if="!isFetching && stavkeUtuzenjas && stavkeUtuzenjas.length === 0">
            <span v-text="$t('toplanaApp.stavkeUtuzenja.home.notFound')">No stavkeUtuzenjas found</span>
        </div>
        <div class="table-responsive" v-if="stavkeUtuzenjas && stavkeUtuzenjas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.obracunskiPeriod')">Obracunski Period</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.datumIzdavanjaRacuna')">Datum Izdavanja Racuna</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.datumDospecaRacuna')">Datum Dospeca Racuna</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.zaduzenje')">Zaduzenje</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.ukupnoZaUplatu')">Ukupno Za Uplatu</span></th>
                    <th><span v-text="$t('toplanaApp.stavkeUtuzenja.utuzenje')">Utuzenje</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stavkeUtuzenja in stavkeUtuzenjas"
                    :key="stavkeUtuzenja.id">
                    <td>
                        <router-link :to="{name: 'StavkeUtuzenjaView', params: {stavkeUtuzenjaId: stavkeUtuzenja.id}}">{{stavkeUtuzenja.id}}</router-link>
                    </td>
                    <td>{{stavkeUtuzenja.obracunskiPeriod}}</td>
                    <td>{{stavkeUtuzenja.datumIzdavanjaRacuna}}</td>
                    <td>{{stavkeUtuzenja.datumDospecaRacuna}}</td>
                    <td>{{stavkeUtuzenja.zaduzenje}}</td>
                    <td>{{stavkeUtuzenja.ukupnoZaUplatu}}</td>
                    <td>
                        <div v-if="stavkeUtuzenja.utuzenje">
                            <router-link :to="{name: 'UtuzenjeView', params: {utuzenjeId: stavkeUtuzenja.utuzenje.id}}">{{stavkeUtuzenja.utuzenje.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StavkeUtuzenjaView', params: {stavkeUtuzenjaId: stavkeUtuzenja.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StavkeUtuzenjaEdit', params: {stavkeUtuzenjaId: stavkeUtuzenja.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stavkeUtuzenja)"
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
            <span slot="modal-title"><span id="toplanaApp.stavkeUtuzenja.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stavkeUtuzenja-heading" v-text="$t('toplanaApp.stavkeUtuzenja.delete.question', {'id': removeId})">Are you sure you want to delete this Stavke Utuzenja?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stavkeUtuzenja" v-text="$t('entity.action.delete')" v-on:click="removeStavkeUtuzenja()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./stavke-utuzenja.component.ts">
</script>
