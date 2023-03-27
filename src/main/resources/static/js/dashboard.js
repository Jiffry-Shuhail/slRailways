$(document).ready(function () {
    $('.sidebar-menu-toggler').on('click', function () {
        var target = $(this).data('target');
        $(target)
            .sidebar({
                dinPage: true,
                transition: 'overlay',
                mobileTransition: 'overlay'
            })
            .sidebar('toggle');
    });

    $(window).resize(function(){
        if($(window).width()<768){
            $('#sidebar').removeClass('inverted').addClass('side-navigation');
        }else{
            $('#sidebar').removeClass('side-navigation').addClass('inverted');
        }
    });

});