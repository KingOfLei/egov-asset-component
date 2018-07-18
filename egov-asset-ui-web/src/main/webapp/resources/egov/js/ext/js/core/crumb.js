/**

 *
 */
define(["resources/egov/js/ext/js/util/common-util",], function(Util){
    function  Crumb($) {
        $.extend({
            'crumbPath': function (str, _$symbol) {
                var defaultSymbol = '>';
                if (Util.isNotNullStr(_$symbol)) {
                    defaultSymbol = _$symbol;
                }
                var $activeTab = $('div.content-tabs > nav > div > a.active');
                var $sel2 = $('.menuItem[href="' + $activeTab.data('id') + '"]');
                var $sel1 = $('.menuItem[href="' + $activeTab.data('id') + '"]').parent().parent().siblings();
                var path = [$sel1.text(), $sel2.text()];

                if (Util.isNotNullStr(str)) {
                    path.push(str);
                }
                return path.join(defaultSymbol);
            }
        });
    }
    return Crumb;
});
