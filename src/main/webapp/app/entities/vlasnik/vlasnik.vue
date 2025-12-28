<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.vlasnik.home.title')" id="vlasnik-heading">Vlasnik</span>
            <router-link :to="{name: 'VlasnikCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-vlasnik">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.vlasnik.home.createLabel')">
                    Create a new Vlasnik
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
        
         <div class="card" :class="{ margine: margina }">            
            <div class="card-body">
                <h5 class="card-title"><span v-text="$t('toplanaApp.racun.pretragaRacuna')"></span></h5>
                <div class="row">
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.stan.sifra')"></label>                      
                    </div>
                   <div class="col-3">
                        	<label v-text="$t('toplanaApp.vlasnik.prezime')"></label>                      
                    </div>
                    <div class="col-3">
                       	<label v-text="$t('toplanaApp.vlasnik.ime')"></label>                      
                    </div>
                    <div class="col-1"></div>
                    <div class="col-1"></div>
                </div>
                <div class="row">
                    <div class="col-3">
                        <input type="text" class="form-control" name="period" id="sifra"
                                v-model="search.sifraStana"  
                                />                   
                    </div>
                  	<div class="col-3">
                        <input type="text" class="form-control" name="prezime" id="prezime"
                                v-model="search.prezime"  
                        /> 
                    </div>
                     <div class="col-3">
                        <input type="text" class="form-control" name="ime" id="ime"
                                v-model="search.ime"  
                                /> 
                    </div>
                    <div class="col-1"></div>
                   	<div class="col-1"></div>
                </div>
                    <button type="button" id="save-entity" @click="send" :disabled="isFetching" class="btn btn-primary" style="margin-top:20px">
                    <span v-text="$t('entity.action.posalji')">Save</span>
                </button>
                

            </div>
        </div>
        <div class="alert alert-warning" v-if="!isFetching && vlasniks && vlasniks.length === 0">
            <span v-text="$t('toplanaApp.vlasnik.home.notFound')">No vlasniks found</span>
        </div>
        <div class="table-responsive" v-if="vlasniks && vlasniks.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ime')"><span v-text="$t('toplanaApp.vlasnik.ime')">Ime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('prezime')"><span v-text="$t('toplanaApp.vlasnik.prezime')">Prezime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prezime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('brojRacuna')"><span v-text="$t('toplanaApp.vlasnik.brojRacuna')">Broj Racuna</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brojRacuna'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('partijaRacuna')"><span v-text="$t('toplanaApp.vlasnik.partijaRacuna')">Partija Racuna</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'partijaRacuna'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('naziv')"><span v-text="$t('toplanaApp.vlasnik.naziv')">Naziv</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'naziv'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('toplanaApp.vlasnik.email')">Email</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('psr')"><span v-text="$t('toplanaApp.vlasnik.psr')">Psr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'psr'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="vlasnik in vlasniks"
                    :key="vlasnik.id">
                    <td>
                        <router-link :to="{name: 'VlasnikView', params: {vlasnikId: vlasnik.id}}">{{vlasnik.id}}</router-link>
                    </td>
                    <td>{{vlasnik.ime}}</td>
                    <td>{{vlasnik.prezime}}</td>
                    <td>{{vlasnik.brojRacuna}}</td>
                    <td>{{vlasnik.partijaRacuna}}</td>
                    <td>{{vlasnik.naziv}}</td>
                    <td>{{vlasnik.email}}</td>
                    <td>{{vlasnik.psr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'VlasnikView', params: {vlasnikId: vlasnik.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'VlasnikEdit', params: {vlasnikId: vlasnik.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(vlasnik)"
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
            <span slot="modal-title"><span id="toplanaApp.vlasnik.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-vlasnik-heading" v-text="$t('toplanaApp.vlasnik.delete.question', {'id': removeId})">Are you sure you want to delete this Vlasnik?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-vlasnik" v-text="$t('entity.action.delete')" v-on:click="removeVlasnik()">Delete</button>
            </div>
        </b-modal>
        <div v-show="vlasniks && vlasniks.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./vlasnik.component.ts">
</script>
