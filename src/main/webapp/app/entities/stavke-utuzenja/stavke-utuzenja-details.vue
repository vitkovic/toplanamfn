<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stavkeUtuzenja">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.stavkeUtuzenja.detail.title')">StavkeUtuzenja</span> {{stavkeUtuzenja.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.obracunskiPeriod')">Obracunski Period</span>
                    </dt>
                    <dd>
                        <span>{{stavkeUtuzenja.obracunskiPeriod}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.datumIzdavanjaRacuna')">Datum Izdavanja Racuna</span>
                    </dt>
                    <dd>
                        <span>{{stavkeUtuzenja.datumIzdavanjaRacuna}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.datumDospecaRacuna')">Datum Dospeca Racuna</span>
                    </dt>
                    <dd>
                        <span>{{stavkeUtuzenja.datumDospecaRacuna}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.zaduzenje')">Zaduzenje</span>
                    </dt>
                    <dd>
                        <span>{{stavkeUtuzenja.zaduzenje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.ukupnoZaUplatu')">Ukupno Za Uplatu</span>
                    </dt>
                    <dd>
                        <span>{{stavkeUtuzenja.ukupnoZaUplatu}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stavkeUtuzenja.utuzenje')">Utuzenje</span>
                    </dt>
                    <dd>
                        <div v-if="stavkeUtuzenja.utuzenje">
                            <router-link :to="{name: 'UtuzenjeView', params: {utuzenjeId: stavkeUtuzenja.utuzenje.id}}">{{stavkeUtuzenja.utuzenje.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stavkeUtuzenja.id" :to="{name: 'StavkeUtuzenjaEdit', params: {stavkeUtuzenjaId: stavkeUtuzenja.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stavke-utuzenja-details.component.ts">
</script>
