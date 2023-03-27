$('.ui.sidebar').sidebar({
    context: $('body'),
    transition: 'overlay'
}).sidebar('attach events', '#mobile_item');

$('.ui.top.attached.blue.progress').progress({
    percent: 100
});

$('.slider').glide({
    animationDuration: 1000,
    autoplay: 3000,
    arrowsWrapperClass: 'slider-arrows',
    arrowRightText: '',
    arrowLeftText: ''
});

$('.ui.search.dropdown').dropdown({clearable: true});

$('#date').calendar({
    minDate:new Date(),
    type: 'date',
    onChange: (date, text, mode) => {
        if(date) {
            $('#returnDate').calendar('set date', date);
        }
    }
});

$('#returnDate').calendar({
    minDate:new Date(),
    type: 'date'
});

$(`#returnDate`).hide();
$(`#return`).click(e => {
    if (e.target.checked) {
        $(`#returnDate`).show();
    } else {
        $(`#returnDate`).hide();
    }
});

$('a.item').click(function () {
    $('a.active').removeClass('active');
    $(this).addClass('active');
});

let section = document.querySelectorAll('section');
let lists = document.querySelectorAll('a.item');

function activeLink(li) {
    lists.forEach((item) => $(item).removeClass('active'));
    $(li).addClass('active');
}

window.onscroll = () => {
    section.forEach(sec => {
        let top = window.scrollY + 200;
        let offset = sec.offsetTop;
        let height = sec.offsetHeight;
        let id = sec.getAttribute('id');

        if (top >= offset && top < offset + height) {
            if (id) {
                activeLink($(`[href='#${id}']`));
            } else {
                activeLink($(`[href='#search']`));
            }
        }
    })
};

var input = document.querySelector("#phone");
window.intlTelInput(input, {
    separateDialCode: true,
    initialCountry: "lk",
});

$('#reset').click(()=>{
    $('input').val('');
    $('select').val('');
    $('.ui.search.dropdown').dropdown('clear');
});