$('.ui.raised.link.card').click(e => {
    $('.ui.cards').find('i.check').addClass('visible-non');
    $(e.currentTarget).find('i.check').toggleClass('visible-non');
    let price = $(e.currentTarget).find('input.price').val();
    let passengers = $('#passengers').val();
    let total = passengers * parseFloat(price);
    $('#trainClass').attr('value', $(e.currentTarget).find('input.classe').val());

    $('#select').removeClass('visible-non');
    $('#onePerson').removeClass('visible-non');

    $('#className').text($(e.currentTarget).find('input.classename').val());

    $('#priceTD').text(new Intl.NumberFormat('en-LK', {
        style: 'currency',
        currency: 'LKR'
    }).format(price));

    $('#amount').text(new Intl.NumberFormat('en-LK', {
        style: 'currency',
        currency: 'LKR'
    }).format(`${total}`));

    $('#finalAmount').text(new Intl.NumberFormat('en-LK', {
        style: 'currency',
        currency: 'LKR'
    }).format(`${total}`));
});

$('.ui.calendar.visible-non').calendar({
    type: 'date'
})

if($('#phone').length) {
    var input = document.querySelector("#phone");
    var iti = intlTelInput(input, {
        separateDialCode: true,
        initialCountry: "lk",
    });
}

$('form[action="/userInformation"]').submit(event => {
    event.preventDefault();
    if (isValid()) {
        if($('#phone').length) {
            $('#phone').val(iti.getNumber());
            $('#contact').attr('value', $('#phone').val());
        }
        event.currentTarget.submit();
    }
});

function isValid() {
    var result = true;

    if(!$('#terms').prop('checked')){
        $('#termsMessage').removeClass('hidden').addClass('visible');
        result = false;
    }

    if (!$('#name').val()) {
        $('#name').parent().addClass('error');
        $('#name').next().removeClass('hidden').addClass('visible');
        result = false;
    }

    if (!$('#cardnumber').val()) {
        $('#cardnumber').parent().addClass('error');
        $('#cardNumberMessage').removeClass('hidden').addClass('visible');
        result = false;
    }

    if (!$('#securitycode').val()) {
        $('#securitycode').parent().addClass('error');
        $('#securitycode').next().removeClass('hidden').addClass('visible');
        result = false;
    }

    if (!$('#expirationdate').val()) {
        $('#expirationdate').parent().addClass('error');
        $('#expirationdate').next().removeClass('hidden').addClass('visible');
        result = false;
    }

    if ($('#phone').length && !iti.getNumber()) {
        $('#phoneMessage').parent().addClass('error');
        $('#phoneMessage').removeClass('hidden').addClass('visible');
        result = false;
    }

    if (!$('#trainClass').val()) {
        $('#trainMessage').removeClass('hidden').addClass('visible');
        result = false;
    }

    return result;
}