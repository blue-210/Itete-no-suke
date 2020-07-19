<template>
  <section class="hero">
    <v-container fluid fill-height class="hero__content">
      <v-row>
        <v-col class="hero__content-text">
          <p>Welcome to my website.</p>
          <p>I'm a enginner.</p>
        </v-col>
      </v-row>
    </v-container>
  </section>
</template>

<style lang="scss" scoped>
.hero__content {
    background: url("../assets/sunflower-field.jpg");
    background-size: cover;
    background-position: center center;
    width: 100%;
    height: 50vh;

    @include display_pc {
        height: 100vh;
    }

    &-text {
        color: white;
        text-align: center;
        font-size: 28px;
        font-weight: bold;

        @include display_pc {
            font-size: 40px;
        }
    }
}
</style>
