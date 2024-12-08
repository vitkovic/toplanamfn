<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="vrstaTransakcije">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.vrstaTransakcije.detail.title')">VrstaTransakcije</span> {{vrstaTransakcije.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.vrstaTransakcije.vrsta')">Vrsta</span>
                    </dt>
                    <dd>
                        <span>{{vrstaTransakcije.vrsta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vrstaTransakcije.dodajeVrednost')">Dodaje Vrednost</span>
                    </dt>
                    <dd>
                        <span>{{vrstaTransakcije.dodajeVrednost}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="vrstaTransakcije.id" :to="{name: 'VrstaTransakcijeEdit', params: {vrstaTransakcijeId: vrstaTransakcije.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./vrsta-transakcije-details.component.ts">
</script>
