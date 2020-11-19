<template>
    <div>
        <img src="../../assets/music_border.svg" style="width:150px;">
        <div class="music-bar">
            <span class="bar bar-0">0</span>
            <span class="bar bar-1">1</span>
            <span class="bar bar-2">2</span>
            <span class="bar bar-3">3</span>
            <span class="bar bar-4">4</span>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
.music-bar{
    width:50px;
    height:50px;
    display:block;
    position:relative;
    top: -200px;
    margin:50px auto;
}
.bar{
    display:block;
    background-color:#8D6262;
    height:25px;
    width:3px;
    text-indent:-9999px;
    position:absolute;
    top:13px;
    -webkit-animation:bar 0ms -1000ms linear infinite alternate running; 
    animation:bar 0ms -1000ms linear infinite alternate running;     
  }
@-webkit-keyframes bar {
    0%   {transform: scale(1,0.25); }
    25%  {transform: scale(1,0.5); }
    50%  {transform: scale(1,0.75); }
    75%  {transform: scale(1,1); }
    100% {transform: scale(1,1.25);}
}
@keyframes bar {
    0%   {transform: scale(1,0.25); }
    25%  {transform: scale(1,0.5); }
    50%  {transform: scale(1,0.75); }
    75%  {transform: scale(1,1); }
    100% {transform: scale(1,1.25);}
}
.bar-0{ left:7px; -webkit-animation-duration: 337ms; animation-duration: 337ms;}
.bar-1{ left:16px; -webkit-animation-duration: 321ms; animation-duration: 321ms;}
.bar-2{ left:25px; -webkit-animation-duration: 353ms; animation-duration: 353ms;}
.bar-3{ left:34px; -webkit-animation-duration: 341ms; animation-duration: 341ms;}
.bar-4{ left:42px; -webkit-animation-duration: 327ms; animation-duration: 327ms;}
</style>>
