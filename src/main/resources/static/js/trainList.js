$('#departure').dropdown({clearable: true});
$('#arrival').dropdown({clearable: true});
$('#active').dropdown();

const FILTER_TABLE = $('#filterData').DataTable({
    initComplete: function (settings, json) {
        $('#filterData_info').hide();
        $('#filterData_filter').hide();
        $('#filterData_length').hide();
    }
});

$('#search').keyup(function (e) {
    FILTER_TABLE.search($(this).val()).draw();
});