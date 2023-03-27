$('.ui.sidebar').sidebar({
    context: $('body'),
    transition: 'overlay'
}).sidebar('attach events', '#mobile_item');

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

$('.ui.calendar.visible-non').calendar({
    type: 'date'
})

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
$('.menu .item').tab();

const FILTER_TABLE = $('#filterData').DataTable({
    initComplete: function (settings, json) {
        $('#filterData_info').hide();
        $('#filterData_filter').hide();
        $('#filterData_length').hide();
    },
    createdRow: function(row, data, dataIndex) {
        $(row).on('click', function() {
            $(this).toggleClass('selectable positive');
            $(`#trainSchedule`).attr('value',$(this).children(4).find('input').val());
        });
    }
});

$('.trigger.example .accordion')
    .accordion({
        selector: {
            trigger: '.title .icon'
        }
    });

var isView = false;
$('.ui.secondary.button.title').click(() => {
    isView = !isView;
    $('.ui.accordion').accordion('toggle');
    $('.arrow.circle.icon').removeClass('down').removeClass('up');
    $('.arrow.circle.icon').addClass(isView ? 'up' : 'down');
});

$('#reset').click(()=>{
    $('input').val('');
    $('select').val('');
    $('.ui.search.dropdown').dropdown('clear');
});

$('form[action="/paymentSummary"]').submit(function(event) {
    event.preventDefault();
    if($('#trainSchedule').val()){
        this.submit();
    }
    $('#trainMessage').removeClass('hidden').addClass('visible');
});