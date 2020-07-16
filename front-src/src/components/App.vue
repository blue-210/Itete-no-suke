<template>
  <v-app>
    <Header/>
      <v-content>
        <Home/>
      </v-content>
    <Footer/>
  </v-app>
</template>

<script>
import Header from './Header.vue'
import Home from './Home.vue'
import Footer from './Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Home,
    Footer
  }
}
</script>
