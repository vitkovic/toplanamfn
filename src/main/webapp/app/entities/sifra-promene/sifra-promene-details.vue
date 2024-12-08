<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sifraPromene">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.sifraPromene.detail.title')">SifraPromene</span> {{sifraPromene.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.sifraPromene.sifra')">Sifra</span>
                    </dt>
                    <dd>
                        <span>{{sifraPromene.sifra}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sifraPromene.id" :to="{name: 'SifraPromeneEdit', params: {sifraPromeneId: sifraPromene.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sifra-promene-details.component.ts">
</script>
