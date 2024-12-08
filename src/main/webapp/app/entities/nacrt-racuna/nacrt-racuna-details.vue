<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="nacrtRacuna">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.nacrtRacuna.detail.title')">NacrtRacuna</span> {{nacrtRacuna.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.datumRacuna')">Datum Racuna</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.datumRacuna}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.period')">Period</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.period}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.valutaPlacanja')">Valuta Placanja</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.valutaPlacanja}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.cenaKwh')">Cena Kwh</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.cenaKwh}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.cenaFix')">Cena Fix</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.cenaFix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.cenaOdrzavanje')">Cena Odrzavanje</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.cenaOdrzavanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.cenaOStalo')">Cena O Stalo</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.cenaOStalo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.popust')">Popust</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.popust}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.staroStanjePodstanice')">Staro Stanje Podstanice</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.staroStanjePodstanice}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.novoStanjePodstanice')">Novo Stanje Podstanice</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.novoStanjePodstanice}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.ukupnaPovrsina')">Ukupna Povrsina</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.ukupnaPovrsina}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.utrosakPoM2')">Utrosak Po M 2</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.utrosakPoM2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.pdv1')">Pdv 1</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.pdv1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.pdv2')">Pdv 2</span>
                    </dt>
                    <dd>
                        <span>{{nacrtRacuna.pdv2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.nacrtRacuna.podstanica')">Podstanica</span>
                    </dt>
                    <dd>
                        <div v-if="nacrtRacuna.podstanica">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: nacrtRacuna.podstanica.id}}">{{nacrtRacuna.podstanica.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="nacrtRacuna.id" :to="{name: 'NacrtRacunaEdit', params: {nacrtRacunaId: nacrtRacuna.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./nacrt-racuna-details.component.ts">
</script>
