<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stanjaPodstanice">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.stanjaPodstanice.detail.title')">StanjaPodstanice</span> {{stanjaPodstanice.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstanice.stanje')">Stanje</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstanice.stanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstanice.datumOcitavanja')">Datum Ocitavanja</span>
                    </dt>
                    <dd>
                        <span>{{stanjaPodstanice.datumOcitavanja}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.stanjaPodstanice.podstanica')">Podstanica</span>
                    </dt>
                    <dd>
                        <div v-if="stanjaPodstanice.podstanica">
                            <router-link :to="{name: 'PodstanicaView', params: {podstanicaId: stanjaPodstanice.podstanica.id}}">{{stanjaPodstanice.podstanica.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stanjaPodstanice.id" :to="{name: 'StanjaPodstaniceEdit', params: {stanjaPodstaniceId: stanjaPodstanice.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stanja-podstanice-details.component.ts">
</script>
