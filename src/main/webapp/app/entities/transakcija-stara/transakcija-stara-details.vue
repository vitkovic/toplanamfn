<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="transakcijaStara">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.transakcijaStara.detail.title')">TransakcijaStara</span> {{transakcijaStara.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.datum')">Datum</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.datum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.valuta')">Valuta</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.valuta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.opis')">Opis</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.opis}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.duguje')">Duguje</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.duguje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.potrazuje')">Potrazuje</span>
                    </dt>
                    <dd>
                        <span>{{transakcijaStara.potrazuje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.vrstaTransakcije')">Vrsta Transakcije</span>
                    </dt>
                    <dd>
                        <div v-if="transakcijaStara.vrstaTransakcije">
                            <router-link :to="{name: 'VrstaTransakcijeView', params: {vrstaTransakcijeId: transakcijaStara.vrstaTransakcije.id}}">{{transakcijaStara.vrstaTransakcije.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.sifraDokumenta')">Sifra Dokumenta</span>
                    </dt>
                    <dd>
                        <div v-if="transakcijaStara.sifraDokumenta">
                            <router-link :to="{name: 'SifraDokumentaView', params: {sifraDokumentaId: transakcijaStara.sifraDokumenta.id}}">{{transakcijaStara.sifraDokumenta.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.sifraPromene')">Sifra Promene</span>
                    </dt>
                    <dd>
                        <div v-if="transakcijaStara.sifraPromene">
                            <router-link :to="{name: 'SifraPromeneView', params: {sifraPromeneId: transakcijaStara.sifraPromene.id}}">{{transakcijaStara.sifraPromene.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.cene')">Cene</span>
                    </dt>
                    <dd>
                        <div v-if="transakcijaStara.cene">
                            <router-link :to="{name: 'CeneStareView', params: {ceneStareId: transakcijaStara.cene.id}}">{{transakcijaStara.cene.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.transakcijaStara.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="transakcijaStara.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: transakcijaStara.stan.id}}">{{transakcijaStara.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="transakcijaStara.id" :to="{name: 'TransakcijaStaraEdit', params: {transakcijaStaraId: transakcijaStara.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./transakcija-stara-details.component.ts">
</script>
