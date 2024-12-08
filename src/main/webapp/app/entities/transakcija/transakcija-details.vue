<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="transakcija">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.transakcija.detail.title')">Transakcija</span> {{transakcija.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.datum')">Datum</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.datum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.valuta')">Valuta</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.valuta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.opis')">Opis</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.opis}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.duguje')">Duguje</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.duguje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.potrazuje')">Potrazuje</span>
                    </dt>
                    <dd>
                        <span>{{transakcija.potrazuje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.vrstaTransakcije')">Vrsta Transakcije</span>
                    </dt>
                    <dd>
                        <div v-if="transakcija.vrstaTransakcije">
                            <router-link :to="{name: 'VrstaTransakcijeView', params: {vrstaTransakcijeId: transakcija.vrstaTransakcije.id}}">{{transakcija.vrstaTransakcije.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.sifraDokumenta')">Sifra Dokumenta</span>
                    </dt>
                    <dd>
                        <div v-if="transakcija.sifraDokumenta">
                            <router-link :to="{name: 'SifraDokumentaView', params: {sifraDokumentaId: transakcija.sifraDokumenta.id}}">{{transakcija.sifraDokumenta.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.sifraPromene')">Sifra Promene</span>
                    </dt>
                    <dd>
                        <div v-if="transakcija.sifraPromene">
                            <router-link :to="{name: 'SifraPromeneView', params: {sifraPromeneId: transakcija.sifraPromene.id}}">{{transakcija.sifraPromene.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.cene')">Cene</span>
                    </dt>
                    <dd>
                        <div v-if="transakcija.cene">
                            <router-link :to="{name: 'CeneView', params: {ceneId: transakcija.cene.id}}">{{transakcija.cene.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcija.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="transakcija.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: transakcija.stan.id}}">{{transakcija.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="transakcija.id" :to="{name: 'TransakcijaEdit', params: {transakcijaId: transakcija.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./transakcija-details.component.ts">
</script>
