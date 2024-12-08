<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="opomena">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.opomena.detail.title')">Opomena</span> {{opomena.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.opomena.datumOpomene')">Datum Opomene</span>
                    </dt>
                    <dd>
                        <span>{{opomena.datumOpomene}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.opomena.zakljucniDatum')">Zakljucni Datum</span>
                    </dt>
                    <dd>
                        <span>{{opomena.zakljucniDatum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.opomena.iznos')">Iznos</span>
                    </dt>
                    <dd>
                        <span>{{opomena.iznos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.opomena.datumIzmirenja')">Datum Izmirenja</span>
                    </dt>
                    <dd>
                        <span>{{opomena.datumIzmirenja}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.opomena.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="opomena.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: opomena.stan.id}}">{{opomena.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="opomena.id" :to="{name: 'OpomenaEdit', params: {opomenaId: opomena.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./opomena-details.component.ts">
</script>
