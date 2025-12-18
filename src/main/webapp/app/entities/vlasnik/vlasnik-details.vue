<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="vlasnik">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.vlasnik.detail.title')">Vlasnik</span> {{vlasnik.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.ime')">Ime</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.ime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.prezime')">Prezime</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.prezime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.brojRacuna')">Broj Racuna</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.brojRacuna}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.partijaRacuna')">Partija Racuna</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.partijaRacuna}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.naziv')">Naziv</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.naziv}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.vlasnik.psr')">Psr</span>
                    </dt>
                    <dd>
                        <span>{{vlasnik.psr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="vlasnik.id" :to="{name: 'VlasnikEdit', params: {vlasnikId: vlasnik.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./vlasnik-details.component.ts">
</script>
