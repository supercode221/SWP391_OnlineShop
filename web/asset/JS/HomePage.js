document.addEventListener('DOMContentLoaded', function () {
    const swiper = new Swiper('.swiper', {
        slidesPerView: 1, // Hiển thị 1 slide tại một thời điểm
        spaceBetween: 30, // Khoảng cách giữa các slide
        loop: true, // Cho phép lặp lại các slide
        autoplay: {
            delay: 3000, // Tự động chuyển slide sau 3 giây
            disableOnInteraction: false, // Không tắt tự động khi có tương tác
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true, // Cho phép click vào chấm phân trang
        },
        navigation: {
            nextEl: '.swiper-button-next', // Nút tiếp theo
            prevEl: '.swiper-button-prev', // Nút trước đó
        }
    });
});
