<template>
    <div id="landing_page">
            <div id="landing_wrap">
                <img id="arrow" src="../assets/toon_arrow.gif"><br>
                <router-link to="/login">
                    <img id="landing_book" src="../assets/intro_book.png">
                </router-link>
            </div>
    </div>
</template>

<script>
export default {

    mounted() {
        var w = window.innerWidth
        var h = window.innerHeight
        $('#landing_page').height(h);
        $('#landing_page').width(w);
        window.addEventListener("resize", this.handleResize)
        
        //preload image
        var image = new Image();
        image.src = '../assets/login_bg.png'
    },
    methods: {
        handleResize(e){
            var w = window.innerWidth
            var h = window.innerHeight
            $('#landing_page').width(w);
            $('#landing_book').css('top', '50%')
        },
        
    }
}
</script>

<style scoped>
#landing_page {
    background: url('../assets/intro_bg.png');
    background-position: bottom center;
}
#landing_book {
    position: relative;
    bottom: 120px;
    height: 120px;
}
#landing_wrap {
    position: relative;
    top: 50%
}
#arrow {
    width: 150px;
    position: relative;
    bottom: 120px;
    left: 150px;
}
</style>