<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.analiticki')" id="transakcija-heading"></span>
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
                        <!--
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
                        -->
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
            <b-row style="margin-top:10px">
                <b-col>
                    <b-table striped sort-compare-locale="sr-Cyrl"
                        :items="transakcije" 
                        :filter="filter" 
                        :per-page="perPage" 
                        :fields="fields"
                        :current-page="currentPage" >
                            <template slot="bottom-row" slot-scope="data">
                                    <td v-for="(field, i) in data.fields" :key="i" style="background-color:#0f7864;color:white">
                                        <div  v-if="i==4">
                                            <div class="mt-1" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.lokali')">Lokali</div>
                                            <div class="mt-1" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.ostali')">Ostali</div>
                                            <div class="mt-1" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.ukupno')">Ukupno</div>
                                        </div>
                                        <div v-if="i==5">
                                            <div class="mt-1">{{lokali.duguje}}</div>
                                            <div class="mt-1">{{ostali.duguje}}</div>
                                            <div class="mt-1">{{ukupno.duguje}}</div>
                                        </div>
                                        <div v-if="i==6">
                                            <div class="mt-1">{{lokali.potrazuje}}</div>
                                            <div class="mt-1">{{ostali.potrazuje}}</div>
                                            <div class="mt-1">{{ukupno.potrazuje}}</div>
                                        </div>
                                    </td>                               
                            </template>                            
                    </b-table>                   
                   <b-pagination v-model="currentPage" class="customPagination" :total-rows="rows" :per-page="perPage"></b-pagination>
                   
                </b-col>
            </b-row>     
            <b-row class="mt-4">
            <b-col sm="2">
                <b-button  v-on:click="stampanje()"
                        variant="info"
                        class="btn ">                    
                    <span class="d-none d-md-inline" v-text="$t('entity.action.stampanje')">Stampanje</span>
                </b-button>
            </b-col>            
        </b-row>      
        </div>



        

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

<script lang="ts" src="./knjigovodstveni-dnevnik-analiticki.component.ts">
</script>
