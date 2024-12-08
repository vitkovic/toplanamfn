<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="ugovorRate">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.ugovorRate.detail.title')">UgovorRate</span> {{ugovorRate.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.ugovorRate.brojRata')">Broj Rata</span>
                    </dt>
                    <dd>
                        <span>{{ugovorRate.brojRata}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ugovorRate.datumSklapanja')">Datum Sklapanja</span>
                    </dt>
                    <dd>
                        <span>{{ugovorRate.datumSklapanja}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ugovorRate.datumDospeca')">Datum Dospeca</span>
                    </dt>
                    <dd>
                        <span>{{ugovorRate.datumDospeca}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ugovorRate.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="ugovorRate.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: ugovorRate.stan.id}}">{{ugovorRate.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="ugovorRate.id" :to="{name: 'UgovorRateEdit', params: {ugovorRateId: ugovorRate.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ugovor-rate-details.component.ts">
</script>
