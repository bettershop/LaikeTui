<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  beforeCreate(){
    let hostname = window.location.hostname;
    let api_base_api = "http://"+hostname+":18168" ;
    localStorage.setItem("api_url",api_base_api);
    console.log(localStorage.getItem("api_url"));
  }
}
</script>
