// @ts-nocheck
<template>
    <b-navbar toggleable="md" type="dark" class="bg-primary">
        <b-navbar-brand class="logo" b-link to="/">
            <span class="logo-img"></span>
            <span v-text="$t('global.title')" class="navbar-title">toplana</span> 
        </b-navbar-brand>      
        <b-navbar-toggle 
        right 
        class="jh-navbar-toggler d-lg-none" 
        href="javascript:void(0);"  
        data-toggle="collapse" 
        target="header-tabs" 
        aria-expanded="false" 
        aria-label="Toggle navigation">
            <font-awesome-icon icon="bars" />
        </b-navbar-toggle>
           
        <b-collapse is-nav id="header-tabs">
            <b-navbar-nav class="ml-auto">
               <!--
                <b-nav-item to="/" exact>
                    <span>                   
                        <span v-text="$t('global.menu.home')">Home</span>
                    </span>
                </b-nav-item>
                -->

                <b-nav-item-dropdown
                    right
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.admin.parametri')">Parametri</span>
                    </span>
                    <b-dropdown-item to="/tip-potrosaca">                        
                        <span v-text="$t('global.menu.entities.tipPotrosaca')">TipPotrosaca</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/podstanica">                        
                        <span v-text="$t('global.menu.entities.podstanica')">Podstanica</span>
                    </b-dropdown-item>
                   <b-dropdown-item to="/cene">                        
                        <span v-text="$t('global.menu.entities.cene')">Cene</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/vrsta-transakcije">                        
                        <span v-text="$t('global.menu.entities.vrstaTransakcije')">VrstaTransakcije</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sifra-dokumenta">                        
                        <span v-text="$t('global.menu.entities.sifraDokumenta')">SifraDokumenta</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sifra-promene">                        
                        <span v-text="$t('global.menu.entities.sifraPromene')">SifraPromene</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/cene-stare">                        
                        <span v-text="$t('global.menu.entities.ceneStare')">CeneStare</span>
                    </b-dropdown-item>
                    <!--
                    <b-dropdown-item to="/parametri">                        
                        <span v-text="$t('global.menu.entities.parametri')">Parametri</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/parametri-istorija">                        
                        <span v-text="$t('global.menu.entities.parametriIstorija')">ParametriIstorija</span>
                    </b-dropdown-item>
                    -->
                </b-nav-item-dropdown>


                <b-nav-item-dropdown
                    right
                    id="racuni-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.racuni.main')"></span>
                    </span>               
                    <b-dropdown-item to="/stanja-podstanice">
                        <span v-text="$t('global.menu.entities.stanjaPodstanice')">StanjaPodstanice</span>
						</b-dropdown-item>     
					   
                    <b-dropdown-item to="/nacrt-racuna">                        
                        <span v-text="$t('global.menu.racuni.izradaRacuna')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/racun">
                        <span v-text="$t('global.menu.racuni.pregledi')"></span>
                    </b-dropdown-item>                    
                    <b-dropdown-item to="/transakcija-zaduzenje">                        
                        <span v-text="$t('global.menu.racuni.zaduzenje')"></span>
                    </b-dropdown-item> 
                    <b-dropdown-item to="/racun-banka">                        
                        <span v-text="$t('global.menu.racuni.datotekeZaBanku')"></span>
                    </b-dropdown-item>                    
                </b-nav-item-dropdown>

                 <b-nav-item-dropdown
                    right
                    id="izvodi-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.razduzenje.main')"></span>
                    </span>                    
                    <b-dropdown-item to="/izvod">                        
                        <span v-text="$t('global.menu.entities.izvodi')"></span>
                    </b-dropdown-item> 
                    <b-dropdown-item to="/stavke-izvoda">                        
                        <span v-text="$t('global.menu.entities.stavkeIzvoda')"></span>
                    </b-dropdown-item>                    
                    <b-dropdown-item to="/transakcija-razduzenje">                        
                        <span v-text="$t('global.menu.razduzenje.main')"></span>
                    </b-dropdown-item>  
                    <b-dropdown-item to="/izvod-postanska">                        
                        <span v-text="$t('global.menu.razduzenje.datotekePostanska')"></span>
                    </b-dropdown-item>                   
                    <b-dropdown-item to="/stavke-izvoda-postanska">                        
                        <span v-text="$t('global.menu.entities.stavkeIzvodaPostanska')"></span>
                    </b-dropdown-item>                    
                </b-nav-item-dropdown>

                <b-nav-item-dropdown
                    right
                    id="izvodi-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.transakcija.main')"></span>
                    </span>                    
                    <b-dropdown-item to="/transakcija">                        
                        <span v-text="$t('global.menu.entities.transakcije')"></span>
                    </b-dropdown-item> 
                     <b-dropdown-item to="/knjigovodstveni-dnevnik">                        
                        <span v-text="$t('global.menu.entities.knjigovodstveniDnevnik')"></span>
                    </b-dropdown-item>                                                         
                </b-nav-item-dropdown>


                <b-nav-item-dropdown
                    right
                    id="racuni-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.korisnici.main')"></span>
                    </span>           
                     <b-dropdown-item to="/opomena">
                        <span v-text="$t('global.menu.entities.opomena')">Opomena</span>
                    </b-dropdown-item>         
                    <b-dropdown-item to="/utuzenje">
                        <span v-text="$t('global.menu.entities.utuzenje')">Utuzenje</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/stavke-utuzenja">
                        <span v-text="$t('global.menu.entities.stavkeUtuzenja')">StavkeUtuzenja</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/ugovor-rate">
                        <span v-text="$t('global.menu.entities.ugovorRate')">UgovorRate</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>

                <b-nav-item-dropdown
                    right
                    id="racuni-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.maticniPodaci.main')"></span>
                    </span>                    
                    <b-dropdown-item to="/stan">                        
                        <span v-text="$t('global.menu.maticniPodaci.stanoviLokali')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/vlasnik">                        
                        <span v-text="$t('global.menu.maticniPodaci.vlasnici')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/ostali-racuni">                        
                        <span v-text="$t('global.menu.entities.ostaliRacuni')"></span>
                    </b-dropdown-item>                   
                </b-nav-item-dropdown>

                <b-nav-item-dropdown
                    right
                    id="entity-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.pregledi.main')"></span>
                    </span>                   
                    
                    <b-dropdown-item to="/transakcija">
                        <span v-text="$t('global.menu.entities.transakcija')">Transakcija</span>
                    </b-dropdown-item>                   
                  <!--  
                    <b-dropdown-item to="/izvod">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.izvod')">Izvod</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/stavke-izvoda">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.stavkeIzvoda')">StavkeIzvoda</span>
                    </b-dropdown-item>
                  -->  
                    <b-dropdown-item to="/transakcija-stara">
                        <span v-text="$t('global.menu.entities.transakcijaStara')">TransakcijaStara</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/stanja-podstanice-za-racun">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.stanjaPodstaniceZaRacun')">StanjaPodstaniceZaRacun</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/stan-stanje">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.stanStanje')">StanStanje</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/vlasnik">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.vlasnik')">Vlasnik</span>
                    </b-dropdown-item>
                    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    right
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.admin.main')">Administration</span>
                    </span>
                    <b-dropdown-item to="/admin/user-management" active-class="active">
                        <font-awesome-icon icon="user" />
                        <span v-text="$t('global.menu.admin.userManagement')">User management</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-metrics" active-class="active">
                        <font-awesome-icon icon="tachometer-alt" />
                        <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-health" active-class="active">
                        <font-awesome-icon icon="heart" />
                        <span v-text="$t('global.menu.admin.health')">Health</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-configuration" active-class="active">
                        <font-awesome-icon icon="list" />
                        <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/audits" active-class="active">
                        <font-awesome-icon icon="bell" />
                        <span v-text="$t('global.menu.admin.audits')">Audits</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/logs" active-class="active">
                        <font-awesome-icon icon="tasks" />
                        <span v-text="$t('global.menu.admin.logs')">Logs</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs" active-class="active">
                        <font-awesome-icon icon="book" />
                        <span v-text="$t('global.menu.admin.apidocs')">API</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
                    <span slot="button-content">
                        <font-awesome-icon icon="flag" />
                        <span v-text="$t('global.menu.language')">Language</span>
                    </span>
                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
                        :class="{ active: isActiveLanguage(key)}">
                        {{value.name}}
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    right
                    href="javascript:void(0);"
                    id="account-menu"
                    :class="{'router-link-active': subIsActive('/account')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">                        
                        <span v-text="$t('global.menu.account.main')">
                            Account
                        </span>
                    </span>
                    <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                        <font-awesome-icon icon="wrench" />
                        <span v-text="$t('global.menu.account.settings')">Settings</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                        <font-awesome-icon icon="lock" />
                        <span v-text="$t('global.menu.account.password')">Password</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout" active-class="active">
                        <font-awesome-icon icon="sign-out-alt" />
                        <span v-text="$t('global.menu.account.logout')">Sign out</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login" active-class="active">
                        <font-awesome-icon icon="sign-in-alt" />
                        <span v-text="$t('global.menu.account.login')">Sign in</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated" active-class="active">
                        <font-awesome-icon icon="user-plus" />
                        <span v-text="$t('global.menu.account.register')">Register</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}


@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span{
    display:none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url("../../../content/images/Logo-MFN.jpg") no-repeat center
    center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
