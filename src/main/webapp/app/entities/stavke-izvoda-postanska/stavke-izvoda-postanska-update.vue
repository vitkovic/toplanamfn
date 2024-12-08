<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stavkeIzvoda.home.knjizenje" v-text="$t('toplanaApp.stavkeIzvoda.home.knjizenje')"></h2>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.valuta')"></span>
                    </div>
                    <div class="col-2">
                        {{stavkeIzvoda.valuta ? $d(Date.parse(stavkeIzvoda.valuta), 'short') : ''}}
                    </div>
                    <div class="col-1">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.sifraStana')"></span>
                    </div>
                    <div class="col-2">
                        <input type="text" class="form-control" id="pozivOdobrenja" name="pozivOdobrenja"
                               v-model="stavkeIzvoda.brojPartijePoverioc"  />
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')"></span>
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold">
                            {{ stavkeIzvoda.rasporedjena ? 'Да' : 'Не' }}
                        </span>
                    </div>
                </div>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.iznos')"></span>
                    </div>
                    <div class="col-3">                        
                        {{stavkeIzvoda.iznos | currency('')}}
                    </div>
                    
                </div>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.brojTekucegRacunaPuni')"></span>
                    </div>
                    <div class="col-3">                        
                        {{stavkeIzvoda.brojTekucegRacuna}}
                    </div>
                    <div class="col-3">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.imeVlasnikaTekucegRacunaPuni')"></span>
                    </div>
                    <div class="col-3">
                        <span>
                            {{stavkeIzvoda.ime}}
                        </span> 
                    </div>                   
                </div>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.adresaVlasnika')"></span>
                    </div>
                    <div class="col-3">                        
                        {{stavkeIzvoda.adresa}}
                    </div>
                    <div class="col-3">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.posta')"></span>
                    </div>
                    <div class="col-3">
                        <span>
                            {{stavkeIzvoda.posta}}
                        </span> 
                    </div>                   
                </div>


                
                
                
                
                
                
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="button" id="save-entity" v-on:click="knjizenje()"
                    :disabled="stavkeIzvoda.rasporedjena == true || isSaving" class="btn btn-primary">
                        <span v-text="$t('entity.action.knjizenje')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stavke-izvoda-postanska-update.component.ts">
</script>
