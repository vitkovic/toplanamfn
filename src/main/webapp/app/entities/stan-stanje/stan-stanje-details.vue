<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stanStanje">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.stanStanje.detail.title')">StanStanje</span> {{stanStanje.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.stanStanje.sifra')">Sifra</span>
                    </dt>
                    <dd>
                        <span>{{stanStanje.sifra}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanStanje.datum')">Datum</span>
                    </dt>
                    <dd>
                        <span>{{stanStanje.datum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanStanje.vrednost')">Vrednost</span>
                    </dt>
                    <dd>
                        <span>{{stanStanje.vrednost}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanStanje.stan_id')">Stan Id</span>
                    </dt>
                    <dd>
                        <span>{{stanStanje.stan_id}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanStanje.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="stanStanje.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: stanStanje.stan.id}}">{{stanStanje.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stanStanje.id" :to="{name: 'StanStanjeEdit', params: {stanStanjeId: stanStanje.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stan-stanje-details.component.ts">
</script>
