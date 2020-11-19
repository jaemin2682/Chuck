<template>
    <div>
        <div class="noResult">        
            <img src="../../assets/gallery/Oops.svg" style="width:200px;">
            <p>
                왼쪽 페이지에서<br>
                인물을 선택해 주세요!
            </p>
        </div>
    </div>
</template>

<script>
export default {
    data: () => ({
        snackbar: false,
        text: `Hello, I'm a snackbar`,
    }),
}
</script>

<style>
.noResult {
    margin-top: 280px;
    text-align: center;
    color: #C0C0C0;
}
</style>