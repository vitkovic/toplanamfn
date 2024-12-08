<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <div v-if="stavkeIzvoda">
                <h2 class="jh-entity-heading">
                    <span v-text="$t('toplanaApp.stavkeIzvoda.detail.title', {'id': stavkeIzvoda.id})">StavkeIzvoda</span> 
                </h2>
                <div class="row" style="margin-top:20px;">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.datum')">Datum</span> 
                    </div>
                    <div class="col-2">
                        <span>{{stavkeIzvoda.datumValute ? $d(Date.parse(stavkeIzvoda.datumValute), 'short') : ''}}</span>
                    </div>
                    <div class="col-6">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')"></span>:
                        <span>{{ stavkeIzvoda.rasporedjena ? 'Да' : 'Не' }}</span>
                    </div>
                    
                </div>
                <div class="row" style="margin-top:10px;">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.nazivZaduzenja')"></span>
                    </div>
                    <div class="col-5">
                        <span>{{stavkeIzvoda.nazivZaduzenja}}</span>
                    </div>                    
                </div>
                <div class="row" style="margin-top:10px;">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.pozivNaBroj')"></span>
                    </div>
                    <div class="col-5">
                        <span>{{stavkeIzvoda.pozivOdobrenja}}</span>
                    </div>                    
                </div>
                <div class="row" style="margin-top:10px;">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.iznos')">Iznos</span>
                    </div>
                    <div class="col-5">
                        <span>{{stavkeIzvoda.iznos}}</span>
                    </div>                    
                </div>
                <div class="row" style="margin-top:10px;">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.opis')"></span>
                    </div>
                    <div class="col-10">
                        <span>{{stavkeIzvoda.svrhaDoznake}}</span>
                    </div>                    
                </div>
                 <div class="row" style="margin-top:10px;" v-if="stavkeIzvoda.izvod">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.izvod')"></span>
                    </div>
                    <div class="col-10">
                        <div >
                            <router-link :to="{name: 'IzvodView', params: {izvodId: stavkeIzvoda.izvod.id}}">{{stavkeIzvoda.izvod.id}}</router-link>
                        </div>
                    </div>                    
                </div>
                <div class="row" style="margin-top:10px;margin-bottom:30px;" v-if="stavkeIzvoda.transakcija">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.transakcija')">Transakcija</span>
                    </div>
                    <div class="col-10">
                        <div v-if="stavkeIzvoda.transakcija">
                            <router-link :to="{name: 'TransakcijaView', params: {transakcijaId: stavkeIzvoda.transakcija.id}}">{{stavkeIzvoda.transakcija.id}}</router-link>
                        </div>
                    </div>                    
                </div>
                <div class="row" style="margin-top:10px;margin-bottom:30px;" v-if="stavkeIzvoda.transakcijaStara">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.stavkeIzvoda.transakcijaStara')">Transakcija Stara</span>
                    </div>
                    <div class="col-10">
                        <div >
                            <router-link :to="{name: 'TransakcijaStaraView', params: {transakcijaStaraId: stavkeIzvoda.transakcijaStara.id}}">{{stavkeIzvoda.transakcijaStara.id}}</router-link>
                        </div>
                    </div>                    
                </div>


                
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
               <!--
                <router-link v-if="stavkeIzvoda.id" :to="{name: 'StavkeIzvodaEdit', params: {stavkeIzvodaId: stavkeIzvoda.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
                -->
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stavke-izvoda-postanska-details.component.ts">
</script>
