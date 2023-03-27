$('.ui.dropdown').dropdown();
$('.ui.calendar').calendar({
    type: 'time',
    blur:true,
    formatter: {
        time: function (date, settings, forCalendar) {
            return `${date.getHours()}:${String(date.getMinutes()).padStart(2, '0')}:${String(date.getSeconds()).padStart(2, '0')}`;
        }
    },
    text:{
        am: '',
        pm: ''
    }});

const regex = /^\d{1,2}:\d{2}:\d{2}$/;
if($('#datatable tbody tr').length===0){
    $('#datatable').hide();
}
$('#add').click(() => {
    let station = $('#station').val();
    let duration = $('#duration').val().trim();
    if (isValidStation(station, duration)) {
        $('#datatable').show();
        let size = $('#datatable tbody tr').length;
        let isAvailable = false;
        $('#datatable > tbody  > tr').each((index, tr) => {
            if ($(tr).eq(0).find('input').val() === station) {
                if ($(tr).find("td:eq(1)").find('input').val() !== duration) {
                    $(tr).find("td:eq(1)").find('input').val(duration);
                    $(tr).find("td:eq(1)").find('input').attr("value", duration);
                    $(tr).find('td:eq(1)').find('span').text(duration);
                }
                isAvailable = true;
            }
        });

        if (!isAvailable) {
            $('#datatable tbody').append(`<tr>
            <td>
                <input style="display: none" name="stationsDurations[${size}].station" value="${station}" >
                ${$("#station option:selected").text()}
            </td>
            <td>
                <input style="display: none" name="stationsDurations[${size}].duration" value="${duration}" >
                <span>${duration}</span>
            </td>
            <td class="selectable remove middle center aligned pointing above negative hand-cursor">
                <i class="trash icon"></i>
            </td>
        </tr>`);
            $('.remove').click(removeRow);
        }

        $('#station').val('');
        $('#station').dropdown('clear');
        $('#duration').val('');
    }
});

function isValidStation(station, duration) {
    if (station) {
        if (duration) {
            if (regex.test(duration)) {
                return true;
            } else {
                $.suiAlert({
                    title: 'Invalid Value Alerts',
                    description: 'Please Enter the Valid Duration',
                    type: 'warning',
                    time: '3',
                    position: 'top-right'
                });
            }
        } else {
            $.suiAlert({
                title: 'Empty Alerts',
                description: 'Please Enter the Duration',
                type: 'warning',
                time: '3',
                position: 'top-right'
            });
        }
    } else {
        $.suiAlert({
            title: 'Empty Alerts',
            description: 'Please Select the Station',
            type: 'warning',
            time: '3',
            position: 'top-right',
            icon: 'train icon'
        });
    }

    return false;
}

function removeRow() {
    $(this).closest('tr').remove();
    $('#datatable > tbody  > tr').each((index, tr) => {
        $(tr).eq(0).find('input').attr('name', `stationsDurations[${index}].station`);
        $(tr).eq(1).find('input').attr('name', `stationsDurations[${index}].duration`);
    });

    if($('#datatable tbody tr').length===0){
        $('#datatable').hide();
    }
}

if($('#classTable tbody tr').length===0){
    $('#classTable').hide();
}
$('#addClasses').click(() => {
    let classes=$('#classes').val();
    let seats=$('#seats').val().trim();
    let price=$('#price').val().trim();
    if(isValidClasses(classes, seats,price)){
        $('#classTable').show();
        let size = $('#classTable tbody tr').length;
        let isAvailable = false;
        $('#classTable > tbody  > tr').each((index, tr) => {
            if ($(tr).eq(0).find('input').val() === classes) {
                if ($(tr).find("td:eq(1)").find('input').val() !== seats) {
                    $(tr).find("td:eq(1)").find('input').val(seats);
                    $(tr).find("td:eq(1)").find('input').attr("value", seats);
                    $(tr).find('td:eq(1)').find('span').text(seats);

                    $(tr).eq(2).find('input').val(price);
                    $(tr).eq(2).find('input').attr("value", price);
                    $(tr).eq(2).find('span').text(price);

                    $(tr).find("td:eq(2)").find('input').val(price);
                    $(tr).find("td:eq(2)").find('input').attr("value", price);
                    $(tr).find('td:eq(2)').find('span').text(price);
                }
                isAvailable = true;
            }
        });

        if (!isAvailable) {
            $('#classTable tbody').append(`<tr>
            <td>
                <input style="display: none" name="trainHasClasses[${size}].trainClass" value="${classes}" >
                ${$("#classes option:selected").text()}
            </td>
            <td>
                <input style="display: none" name="trainHasClasses[${size}].seats" value="${seats}" >
                <span>${seats}</span>
            </td>
            <td>
                <input style="display: none" class="price" name="trainHasClasses[${size}].price" value="${price}" >
                <span class="price">${price}</span>
            </td>
            <td class="selectable removeClass middle center aligned negative hand-cursor">
                <i class="trash icon"></i>
            </td>
        </tr>`);
            $('.removeClass').click(removeClassRow);
        }

        $('#classes').val('');
        $('#classes').dropdown('clear');
        $('#seats').val('');
        $('#price').val('');
    }
});
const positiverRegex = /^\d+(\.\d+)?$/;

function isValidClasses(classes, seats,price) {
    if (classes) {
        if (seats) {
            if (positiverRegex.test(seats)) {
                if (price) {
                    if (!isNaN(parseFloat(price))) {
                        return true;
                    } else {
                        $.suiAlert({
                            title: 'Invalid Value Alerts',
                            description: 'Please Enter the Valid Seats Price',
                            type: 'warning',
                            time: '3',
                            position: 'top-right'
                        });
                    }
                } else {
                    $.suiAlert({
                        title: 'Empty Alerts',
                        description: 'Please Enter the Seats Price',
                        type: 'warning',
                        time: '3',
                        position: 'top-right'
                    });
                }
            } else {
                $.suiAlert({
                    title: 'Invalid Value Alerts',
                    description: 'Please Enter the Valid Seats Count',
                    type: 'warning',
                    time: '3',
                    position: 'top-right'
                });
            }
        } else {
            $.suiAlert({
                title: 'Empty Alerts',
                description: 'Please Enter the Seat Count',
                type: 'warning',
                time: '3',
                position: 'top-right'
            });
        }
    } else {
        $.suiAlert({
            title: 'Empty Alerts',
            description: 'Please Select the Class',
            type: 'warning',
            time: '3',
            position: 'top-right',
            icon: 'train icon'
        });
    }

    return false;
}

function removeClassRow() {
    $(this).closest('tr').remove();
    $('#classTable > tbody  > tr').each((index, tr) => {
        $(tr).eq(0).find('input').attr('name', `trainHasClasses[${index}].trainClass`);
        $(tr).eq(1).find('input').attr('name', `trainHasClasses[${index}].duration`);
        $(tr).eq(12).find('input').attr('name', `trainHasClasses[${index}].price`);
    });

    if($('#classTable tbody tr').length===0){
        $('#classTable').hide();
    }
}