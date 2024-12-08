<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stanjaPodstaniceZaRacun">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.detail.title')">StanjaPodstaniceZaRacun</span> {{stanjaPodstaniceZaRacun.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.staroStanje')">Staro Stanje</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstaniceZaRacun.staroStanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.novoStanje')">Novo Stanje</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstaniceZaRacun.novoStanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.ukupnaPovrsina')">Ukupna Povrsina</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstaniceZaRacun.ukupnaPovrsina}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstaniceZaRacun.utrosakPoM2')">Utrosak Po M 2</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstaniceZaRacun.utrosakPoM2}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stanjaPodstaniceZaRacun.id" :to="{name: 'StanjaPodstaniceZaRacunEdit', params: {stanjaPodstaniceZaRacunId: stanjaPodstaniceZaRacun.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stanja-podstanice-za-racun-details.component.ts">
</script>
