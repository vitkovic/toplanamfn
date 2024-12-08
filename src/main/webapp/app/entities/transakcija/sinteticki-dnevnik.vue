<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.knjigovodstveniDnevnikSintetika')" id="transakcija-heading"></span>
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
                    <div class="col-3">                                                
                        <label v-text="$t('toplanaApp.racun.odDatuma')"></label>
                    </div>  
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.doDatuma')"></label>
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
                        <b-form-datepicker
                            id="datepicker-dateformat3"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="search.datumDo"
                            locale="sr"                            
                        ></b-form-datepicker> 
                    </div>                            
                </div>
                        
                <button type="button" id="save-entity"  :disabled="isFetching" @click="send"
                                    class="btn btn-primary" style="margin-top:20px">
                    <span v-text="$t('entity.action.posalji')">Save</span>
                </button>                
            </div>
        </div>

        <div v-show="rekapitulacije && rekapitulacije.length > 0 && rekapitulacije[0].datum" style="margin-top:10px;">
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
                        :items="rekapitulacije" 
                        :filter="filter" 
                        :per-page="perPage" 
                        :fields="fields"
                        :current-page="currentPage" >
                            <template slot="bottom-row" slot-scope="data">                   
                                <td style="background-color:#557360;color:white">Ukupno</td>
                                <td style="background-color:#557360;color:white">{{totalDuguje | currency('')}}</td>                                
                                <td style="background-color:#557360;color:white">{{totalPotrazuje | currency('')}}</td>
        
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

<script lang="ts" src="./sinteticki-dnevnik.component.ts">
</script>
