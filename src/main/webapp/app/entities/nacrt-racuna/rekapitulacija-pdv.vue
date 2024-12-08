<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.title')" id="nacrt-racuna-heading">Nacrt Racunas</span>           
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.datum')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.datum}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span  class="bold-font" v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.varijabilniDeo')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.varijabilniDeo | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.fiksniDeo')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.fiksniDeo | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.popust')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.popust | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.ukupno')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.ukupno | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.pdv2')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.pdv2 | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.odrzavanje')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.odrzavanje | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font"  v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.pdv1')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.pdv1 | currency('')}}
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="2">
                <span class="bold-font" v-text="$t('toplanaApp.nacrtRacuna.rekapitulacija.ukupnoZaduzenje')"></span>
            </b-col>
            <b-col sm="2">
                {{rekapitulacija.ukupnoZaduzenje | currency('')}}
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
</template>

<script lang="ts" src="./rekapitulacija-pdv.component.ts">
</script>
