<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.transakcija.home.title')" id="transakcija-heading">Transakcijas</span>
           <!--
            <router-link :to="{name: 'TransakcijaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-transakcija">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.transakcija.home.createLabel')">
                    Create a new Transakcija
                </span>
            </router-link>
            -->
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
                <h5 class="card-title"><span v-text="$t('toplanaApp.transakcija.pretraga')"></span></h5>
                <div class="row">
                    <div class="col-6">
                        <div class="row">
                            <div class="col-6">
                                <label v-text="$t('toplanaApp.racun.odDatuma')"></label>
                            </div>
                            <div class="col-6">
                                <label v-text="$t('toplanaApp.stan.sifra')"></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <b-form-datepicker
                                    id="datepicker-dateformat2"
                                    :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                    v-model="search.datumOd"
                                    locale="sr"                            
                                ></b-form-datepicker> 
                            </div>
                            <div class="col-6">
                                <input type="text" class="form-control" name="sifra" id="sifra"
                                v-model="search.sifraStana"  
                                />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <label v-text="$t('toplanaApp.racun.doDatuma')"></label>
                            </div>
                            <div class="col-6">
                               <label v-text="$t('toplanaApp.vlasnik.prezime')"></label> 
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <b-form-datepicker
                                    id="datepicker-dateformat3"
                                    :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                    v-model="search.datumDo"
                                    locale="sr"                            
                                ></b-form-datepicker> 
                            </div>
                            <div class="col-6">
                                <input type="text" class="form-control" name="prezime" id="prezime"
                                v-model="search.prezime"  
                                />
                            </div>
                        </div>
                        
                    </div>                    
                    <div class="col-3">
                        <div class="row">
                            <div class="col-12">
                                <label v-text="$t('toplanaApp.podstanica.detail.title')"></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <select class="form-control" id="search-podstanica" name="podstanica" v-model="search.podstanica">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="search.podstanica && podstanicaOption.id === search.podstanica ? search.podstanica : podstanicaOption" 
                                    v-for="podstanicaOption in podstanice" :key="podstanicaOption.id">{{podstanicaOption.naziv}} - broj: {{podstanicaOption.broj}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <b-form-checkbox style="margin-top:10px;"
                                    id="checkbox-1"
                                    v-model="search.ukljucen"
                                    name="checkbox-1"                                    
                                    >
                                    <span v-text="$t('toplanaApp.stan.ukljucenoGrejanje')"></span>
                                </b-form-checkbox>
                            </div>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="row">
                            <div class="col-12">
                                <span v-text="$t('toplanaApp.stan.reoni')"></span>
                            </div>
                        </div>
                        <div class="row" style="margin-top:10px;">                            
                            <div class="col-12">
                                <b-form-checkbox-group
                                    v-model="selected"
                                    :options="options"                                
                                    name="flavour-2a"
                                    stacked
                                ></b-form-checkbox-group>                            
                            </div>                            
                        </div>                        
                    </div>                                    
                </div>
                <button type="button" id="save-entity"  :disabled="isFetching" @click="send"
                                    class="btn btn-primary" style="margin-top:20px">
                    <span v-text="$t('entity.action.posalji')">Save</span>
                </button>                
            </div>
        </div>

        <div v-show="transakcije && transakcije.length > 0" style="margin-top:10px;">
            <b-row>
                <b-col md="9">
                    
                </b-col>
                <b-col md="3" style="text-align:right;">
                    <b-form-input v-model="filter" type="search" :placeholder="potrazi"></b-form-input>
                </b-col>
            </b-row>
            <b-row style="margin-left:0px;margin-top:10px">                     
                <b-col>
                    <b-table striped 
                        :items="transakcije" 
                        :filter="filter" 
                        :per-page="perPage" 
                        :fields="fields"
                        :current-page="currentPage" >
                            <template v-slot:cell(actions)="data">
                                <b-button variant="info" @click="prikaziDetalje(data.item.sifra)">
                                    <span v-text="$t('entity.action.details')"></span>
                                </b-button>
                            </template>
                    </b-table>
                    <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"></b-pagination>
                </b-col>
            </b-row>
        </div>


        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="toplanaApp.transakcija.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-transakcija-heading" v-text="$t('toplanaApp.transakcija.delete.question', {'id': removeId})">Are you sure you want to delete this Transakcija?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-transakcija" v-text="$t('entity.action.delete')" v-on:click="removeTransakcija()">Delete</button>
            </div>
        </b-modal>

        

        <!--
        <div v-show="transakcije && transakcije.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
        -->
    </div>
</template>

<script lang="ts" src="./transakcija.component.ts">
</script>
<style>
 .fieldWidth {
      width: 30%
    }
</style>
