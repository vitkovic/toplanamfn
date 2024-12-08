<template>
    <div class="row justify-content-center">
        <div class="col-12" v-if="izvod">
            <div class="row">
                <div class="col-12"  style="text-align:center">
                    <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.izvod.detail.title')">Izvod</span>: {{izvod.brojIzvoda}}</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12" style="text-align:center; font-size:large">
                    <span v-text="$t('toplanaApp.izvod.detail.stanjeIPromenaNaDan')"></span> {{izvod.datumIzvodaZaglavlje}}
                </div>
            </div>
            <div class="row">
                <div class="col-12" >
                    {{izvod.racunIzvoda}}
                </div>
            </div>
            <div class="row">
                <div class="col-12" >
                    {{izvod.naziv}}
                </div>
            </div>
            <hr class="new3">
            <div class="row">
                <div class="col-auto " >
                    <span v-text="$t('toplanaApp.izvod.rbr')"></span>
                </div>
                <div class="col-3" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.racun')"></span><br>
                    <span v-text="$t('toplanaApp.stavkeIzvoda.nazivIMesto')"></span></br>
                    <span v-text="$t('toplanaApp.stavkeIzvoda.svrhaDoznake')"></span></br>
                </div>
                <div class="col-1" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.zaduzenje')"></span>
                </div>
                <div class="col-1" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.odobrenje')"></span>
                </div>
                <div class="col-1" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.sifraPlacanja')"></span>
                </div>
                <div class="col-2" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.podatakZaReklamaciju')"></span>
                </div>
                <div class="col-2" >
                    PBZ</br>
                    PBO</br>
                </div>
                <div class="col-1" >
                    <span v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')" class="text-nowrap"></span>
                </div>
            </div> 
            <hr class="new3">
            <div class="row">
                <div class="col-12">
                    <div class=" grid-striped">
                        <div class="row" v-for="(stavka, index) in izvod.stavkaIzvodas">
                            <div class="col-auto" >
                                {{index+1}}
                            </div>
                            <div class="col-3" >
                                <router-link :to="{name: 'StavkeIzvodaEdit', params: {stavkeIzvodaId: stavka.id}}" 
                                :class="{'red-link': !stavka.rasporedjena, 'blue-link': stavka.rasporedjena }">
                                   <a> 
                                    {{stavka.racunZaduzenja}}<br>
                                    {{stavka.nazivZaduzenja}}<br>
                                    {{stavka.svrhaDoznake}}<br>
                                   </a> 
                                </router-link>
                                
                            </div>
                            <div class="col-1" >
                                <span v-if="stavka.racunZaduzenja === RACUN_ZADUZENJA">
                                    {{stavka.iznos}}
                                </span>                                
                            </div>
                            <div class="col-1" >
                                <span v-if="stavka.racunZaduzenja != RACUN_ZADUZENJA">
                                    {{stavka.iznos}}
                                </span>
                            </div>
                            <div class="col-1" >
                                {{stavka.sifraPlacanja}}
                            </div>
                            <div class="col-2" >
                                {{stavka.podatakZaReklamaciju}}
                            </div>
                            <div class="col-2" >
                                {{stavka.modelPozivaZaduzenja}} {{stavka.pozivZaduzenja}}<br>
                                {{stavka.modelPozivaOdobrenja}} {{stavka.pozivOdobrenja}}
                            </div>
                            <div class="col-1" >
                                <span class="font-weight-bold">
                                {{ stavka.rasporedjena ? 'Да' : 'Не' }}
                                </span>
                            </div>
                        </div>   
                    </div>    
                </div>
            </div>
                

                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="izvod.id" :to="{name: 'IzvodEdit', params: {izvodId: izvod.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            
        </div>
    </div>
</template>

<script lang="ts" src="./izvod-details.component.ts">
</script>
