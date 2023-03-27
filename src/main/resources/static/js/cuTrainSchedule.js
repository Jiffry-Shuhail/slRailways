$('.ui.dropdown').dropdown();

$('#mainDate').calendar({
    minDate:new Date(),
    onChange: (date, text, mode) => {
        if(date) {
            $('#datatable > tbody  > tr').each((index, tr) => {
                let arrival=$(tr).children().eq(1).find('div.duration').calendar('get date');
                let arrivalAdd = moment.utc(`${arrival.getHours()}:${arrival.getMinutes()}:${arrival.getSeconds()}`, 'HH:mm:ss')
                    .add(date.getHours(), 'hour')
                    .add(date.getMinutes(), 'minutes')
                    .add(date.getSeconds(), 'seconds')
                    .format('HH:mm:ss');
                $(tr).children().eq(1).find('input.live').val(arrivalAdd);

                let departure=$(tr).children().eq(2).find('div.duration').calendar('get date');;
                let departureAdd = moment.utc(`${departure.getHours()}:${departure.getMinutes()}:${departure.getSeconds()}`, 'hh:mm:ss')
                    .add(date.getHours(), 'hour')
                    .add(date.getMinutes(), 'minutes')
                    .add(date.getSeconds(), 'seconds')
                    .format('HH:mm:ss');
                $(tr).children().eq(2).find('input.live').val(departureAdd);
            });
        }
    }
});

$('.duration').calendar({
    type: 'time',
    blur: true,
    formatter: {
        time: function (date, settings, forCalendar) {
            return `${date.getHours()}:${String(date.getMinutes()).padStart(2, '0')}:${String(date.getSeconds()).padStart(2, '0')}`;
        }
    },
    text: {
        am: '',
        pm: ''
    }
});

$('#train').change(e => {
    $('body').append(`<a id="link" href="/dashboard/cuTrainSchedule/${$(e.currentTarget).val()}">&nbsp;</a>`);
    $('#link')[0].click();
});