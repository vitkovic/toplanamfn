<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stan">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.stan.detail.title')">Stan</span> {{stan.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.stan.povrsina')">Povrsina</span>
                    </dt>
                    <dd>
                        <span>{{stan.povrsina}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.ulica')">Ulica</span>
                    </dt>
                    <dd>
                        <span>{{stan.ulica}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.ulaz')">Ulaz</span>
                    </dt>
                    <dd>
                        <span>{{stan.ulaz}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.broj')">Broj</span>
                    </dt>
                    <dd>
                        <span>{{stan.broj}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.ukljucen')">Ukljucen</span>
                    </dt>
                    <dd>
                        <span>{{stan.ukljucen}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.sifra')">Sifra</span>
                    </dt>
                    <dd>
                        <span>{{stan.sifra}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.grad')">Grad</span>
                    </dt>
                    <dd>
                        <span>{{stan.grad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.postanskiBroj')">Postanski Broj</span>
                    </dt>
                    <dd>
                        <span>{{stan.postanskiBroj}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.tipPotrosaca')">Tip Potrosaca</span>
                    </dt>
                    <dd>
                        <div v-if="stan.tipPotrosaca">
                            <router-link :to="{name: 'TipPotrosacaView', params: {tipPotrosacaId: stan.tipPotrosaca.id}}">{{stan.tipPotrosaca.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.podstanica')">Podstanica</span>
                    </dt>
                    <dd>
                        <div v-if="stan.podstanica">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: stan.podstanica.id}}">{{stan.podstanica.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stan.vlasnik')">Vlasnik</span>
                    </dt>
                    <dd>
                        <div v-if="stan.vlasnik">
                            <router-link :to="{name: 'VlasnikView', params: {vlasnikId: stan.vlasnik.id}}">{{stan.vlasnik.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stan.id" :to="{name: 'StanEdit', params: {stanId: stan.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stan-details.component.ts">
</script>
