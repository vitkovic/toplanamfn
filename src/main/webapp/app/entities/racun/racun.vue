<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.racun.home.title')" id="racun-heading">Racuns</span>
            <router-link :to="{name: 'RacunCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-racun">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('toplanaApp.racun.home.createLabel')">
                    Create a new Racun
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
        <!--
        <div class="alert alert-warning" v-if="!isFetching && racuns && racuns.length === 0">
            <span v-text="$t('toplanaApp.racun.home.notFound')"></span>
        </div>
        -->

        <div class="card" :class="{ margine: margina }">            
            <div class="card-body">
                <h5 class="card-title"><span v-text="$t('toplanaApp.racun.pretragaRacuna')"></span></h5>
                <div class="row">
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.odDatuma')"></label>                      
                    </div>
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.stan.sifra')"></label>                      
                    </div>
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.podstanica.detail.title')"></label>                      
                    </div>
                    <div class="col-1">
                        <label v-text="$t('toplanaApp.racun.azuriran')"></label>                      
                    </div>
                    <div class="col-1">
                        <label v-text="$t('toplanaApp.racun.proknjizen')"></label>                      
                    </div>
                </div>
                <div class="row">
                    <div class="col-3">
                       <b-form-datepicker
                            id="datepicker-dateformat2"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="search.datumOd"
                            locale="sr"                            
                        ></b-form-datepicker>                      
                    </div>
                    <div class="col-3">
                        <input type="text" class="form-control" name="period" id="sifra"
                                v-model="search.sifraStana"  
                                />                   
                    </div>
                    <div class="col-3">
                        <select class="form-control" id="search-podstanica" name="podstanica" v-model="search.podstanica">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="search.podstanica && podstanicaOption.id === search.podstanica ? search.podstanica : podstanicaOption" 
                            v-for="podstanicaOption in podstanice" :key="podstanicaOption.id">{{podstanicaOption.naziv}} - broj: {{podstanicaOption.broj}}</option>
                        </select>
                      
                    </div>
                    <div class="col-1">
                        <b-form-checkbox
                            id="checkbox-2"
                            v-model="search.azuriran" 
                            name="checkbox-2"                                                                                   
                        >
                        </b-form-checkbox>                    
                    </div>
                    <div class="col-1">
                        <b-form-checkbox
                            id="checkbox-3"
                            v-model="search.proknjizen" 
                            name="checkbox-3"                                                       
                        >
                        </b-form-checkbox>                     
                    </div>
                </div>
                <div class="row" style="margin-top:10px">                    
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.doDatuma')"></label>                      
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
                        <b-form-datepicker
                            id="datepicker-dateformat3"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="search.datumDo"
                            locale="sr"                            
                        ></b-form-datepicker>                   
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
                

                <button type="button" id="save-entity" @click="send" :disabled="isSaving" class="btn btn-primary" style="margin-top:20px">
                    <span v-text="$t('entity.action.posalji')">Save</span>
                </button>
                

            </div>
        </div>



        <div class="table-responsive" v-if="racuns && racuns.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->                    
                    <th v-on:click="changeOrder('datumRacuna')"><span v-text="$t('toplanaApp.racun.datumRacuna')">Datum Racuna</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datumRacuna'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('period')"><span v-text="$t('toplanaApp.racun.period')">Period</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'period'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('utrosakVarijabilni')"><span v-text="$t('toplanaApp.racun.utrosakVarijabilni')">Utrosak Varijabilni</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'utrosakVarijabilni'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('utrosakFiksni')"><span v-text="$t('toplanaApp.racun.utrosakFiksni')">Utrosak Fiksni</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'utrosakFiksni'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('utrosakOdrzavanje')"><span v-text="$t('toplanaApp.racun.utrosakOdrzavanje')">Utrosak Odrzavanje</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'utrosakOdrzavanje'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('stan.sifra')"><span v-text="$t('toplanaApp.racun.stan')">Stan</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stan.vlasnik.prezime')"><span v-text="$t('toplanaApp.vlasnik.prezimeIime')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stan.vlasnik.prezime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('nacrtRacuna.id')"><span v-text="$t('toplanaApp.racun.nacrtRacuna')">Nacrt Racuna</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nacrtRacuna.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="racun in racuns"
                    :key="racun.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'RacunView', params: {racunId: racun.id}}">{{racun.id}}</router-link>
                    </td>
                    -->
                    <td>{{racun.datumRacuna}}</td>
                    <td>{{racun.period}}</td>
                    <!--
                    <td>{{racun.utrosakVarijabilni}}</td>
                    <td>{{racun.utrosakFiksni}}</td>
                    <td>{{racun.utrosakOdrzavanje}}</td>
                    -->
                    <td>
                        <div v-if="racun.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: racun.stan.id}}">{{racun.stan.sifra}}</router-link>
                        </div>
                    </td>
                    <td>{{racun.stan.vlasnik.prezime}} {{racun.stan.vlasnik.ime}}</td>
                    <td>
                        <div v-if="racun.nacrtRacuna">
                            <router-link :to="{name: 'NacrtRacunaView', params: {nacrtRacunaId: racun.nacrtRacuna.id}}">{{racun.nacrtRacuna.period}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RacunView', params: {racunId: racun.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link  :to="{name: 'RacunEdit', params: {racunId: racun.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-if="!racun.proknjizen" v-on:click="prepareRemove(racun)"
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
            <span slot="modal-title"><span id="toplanaApp.racun.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-racun-heading" v-text="$t('toplanaApp.racun.delete.question', {'id': removeId})">Are you sure you want to delete this Racun?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-racun" v-text="$t('entity.action.delete')" v-on:click="removeRacun()">Delete</button>
            </div>
        </b-modal>
        <div v-show="racuns && racuns.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
        <button v-show="racuns && racuns.length > 0" type="button" id="stampanje" @click="stampanje" :disabled="isSaving" class="btn btn-primary" style="margin-top:20px">
            <span v-text="$t('entity.action.stampanje')">Save</span>
        </button>
    </div>
</template>

<script lang="ts" src="./racun.component.ts">
</script>
