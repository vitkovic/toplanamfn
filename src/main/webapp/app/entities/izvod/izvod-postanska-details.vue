<template>
    <div class="row justify-content-center">
        <div class="col-12" v-if="izvod">            
            <div class="row">
                <div class="col-4" style="text-align:center; font-size:large">
                    <h2 v-text="$t('toplanaApp.izvod.detail.postanskaStedionica')"></h2>                     
                </div>
                <div class="col-6" style="text-align:center; font-size:large">
                    <h2 v-text="$t('toplanaApp.izvod.detail.trajniNaloziZaPlacanje')"></h2>
                     
                </div>
                <div class="col-2" style="text-align:center; font-size:large">                    
                     <h3>{{izvod.datumIzvodaZaglavlje ? $d(Date.parse(izvod.datumIzvodaZaglavlje), 'short') : ''}}</h3>                     
                </div>
            </div>                                               
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <table class="table table-sm table-striped">
                             <thead>
                                <tr>
                                    <th><span v-text="$t('toplanaApp.izvod.rbr')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.valuta')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.brojTekucegRacuna')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.brojPartijeKodPoverioca')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.imeVlasnikaTekucegRacuna')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.adresaVlasnika')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.posta')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.iznos')"></span></th>
                                    <th><span  v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')"></span></th>
                                </tr>
                             </thead>
                             <tbody>
                                <tr v-for="(stavka) in izvod.stavkeIzvodaPostanska" :key="stavka.id">
                                    <td>{{stavka.broj}}</td>
                                    <td><span>{{$d(Date.parse(stavka.valuta), 'short') }}</span></td>
                                    <td>{{stavka.brojTekucegRacuna}}</td>
                                    <td>
                                        <router-link v-if="stavka.npl == null" :to="{name: 'StavkeIzvodaPostanskaEdit', params: {stavkeIzvodaId: stavka.id}}" 
                                            :class="{'red-link': !stavka.rasporedjena, 'blue-link': stavka.rasporedjena }">
                                        {{stavka.brojPartijePoverioc}}
                                        </router-link>
                                        <span v-if="stavka.npl">{{stavka.brojPartijePoverioc}}</span>
                                    </td> 
                                    <td>{{stavka.ime}}</td>
                                    <td>{{stavka.adresa}}</td>
                                    <td>{{stavka.posta}}</td>
                                    <td>{{stavka.iznos}}</td>
                                    <td>
                                        <span class="font-weight-bold">
                                            {{ stavka.rasporedjena ? 'Да' : 'Не' }}
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                        </table>                        
                    </div>
                    <hr class="new3">
                   <!--
                    <div class="row mt-4">
                        <div class="col-sm-2 offset-sm-6">
                            УКУПНО ЗА УСЛУГУ:
                        </div>
                        <div class="col-sm-4 text-right">
                            {{izvod.ukupnoZaUslugu | currency('')}}
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-sm-2 offset-sm-6">
                            УКУПНО НЕПЛАЋЕНИХ:
                        </div>
                        <div class="col-sm-4 text-right">
                            {{izvod.ukupnoNeplacenih | currency('')}}
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-sm-2 offset-sm-6">
                            ТОТАЛ ПЛАЋЕНИХ:
                        </div>
                        <div class="col-sm-4 text-right">
                            {{izvod.ukupnoPlacenih | currency('')}}
                        </div>
                    </div>-->
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

<script lang="ts" src="./izvod-postanska-details.component.ts">
</script>
