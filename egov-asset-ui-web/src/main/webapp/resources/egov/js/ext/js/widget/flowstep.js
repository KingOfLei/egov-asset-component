/**
 * 对app-tabs的扩展将横向tabs变为纵向
 */
define([
    "app/core/app-core",
    "resources/egov/js/ext/js/base/ext-base",
    "resources/egov/js/ext/js/util/common-util",
], function ($A, ExtBase, CUtil) {

    var FlowStep = ExtBase.extend({

        defaultOption: {},

        init: function () {
            var _self = this;
        },

        render: function () {
            var _self = this;
            var $ele = $('<div>');
            _self.$ele = $ele;
            var data = _self.data;
            var stepName = _self.stepName;
            var stepTime = _self.stepTime;

            if (data.length < 2) {
                throw new Error('[FlowStep] render error...');
                return;
            }

            var $ol = $('<ol class="detail-stepbar flowstep-6">');
            var liStr = '<li><div><div class="step-name"></div><div class="step-no" ><span ></span></div><span></span><div class="step-time"><div class="step-time-wraper"></div></div></div></li>';

            var liList = [];
            _self.liList = liList;
            _self.$ol = $ol;
            for (var i = 0; i < data.length; i++) {
                var $li = $(liStr);
                $ol.append($li);
                liList.push($li);
                $li.data('data', data[i]);
                _self.getEleAttach($li);
                $li.stepName.text(data[i][stepName]);
                $li.stepTime.text(data[i][stepTime]);
                $li.stepNo.data('value', i + 1);
                $li.stepNo.text(i + 1);
            }
            liList[0].addClass('step-first');
            liList[0].find('>div').addClass('step-cur');
            liList[liList.length - 1].addClass('step-last');

            $ele.addClass('flowstep')
            $ele.append($ol);
            $ele.appendTo(_self.$component);
        },
        afterRender: function () {
            var _self = this;
            var l = _self.location;
            if (l && /^\d+$/.test(l)) {
                _self.seek(l, _self.locationType);
            }
        },
        setNo: function ($li) {
            var _self = this;
            if (!$li.div) {
                return;
            }
            _self.getEleAttach($li);
            $li.stepNo.text($li.no);
        },
        clearNo: function ($li) {
            var _self = this;
            if (!$li.div) {
                return;
            }
            _self.getEleAttach($li);
            $li.stepNo.html('&nbsp;');
        },
        getEleAttach: function ($li) {
            var _self = this;
            $li.stepNo = $li.find('.step-no span');
            $li.stepTime = $li.find('.step-time .step-time-wraper');
            $li.no = $li.stepNo.data('value');
            $li.stepName = $li.find('.step-name')
            $li.div = $li.find('>div');
            $.extend($li, _self.data[$li.no - 1]);
            return $li;
        },
        next: function () {
            var _self = this;
            var $cur = _self.getCurrent();
            _self.seek($cur.no + 1, 'cur');
        },
        nextSub: function () {
            var _self = this;
            var liList = _self.liList;
            var $cur = _self.getCurrent();
            var no = $cur.no;
            var type = '';
            if ($cur.div.hasClass('step-cur') && $cur.no !== liList.length) {
            }
            if ($cur.div.hasClass('step-done')) {
                no = no + 1;
                type = 'cur';
            }
            _self.seek(no, type);
        },
        prev: function () {
            var _self = this;
            var $cur = _self.getCurrent();
            var _self = this;
            var liList = _self.liList;
            var $cur = _self.getCurrent();
            var no = $cur.no - 1;
            var type = 'cur';
            if ($cur.div.hasClass('step-done')) {
                no = no + 1;
                type = 'cur';
            }
            _self.seek(no, type);
        },
        prevSub: function () {
            var _self = this;
            var liList = _self.liList;
            var $cur = _self.getCurrent();
            var no = $cur.no;
            var type = '';
            if ($cur.div.hasClass('step-cur')) {
                no = no - 1;
            }
            if ($cur.div.hasClass('step-done')) {
                type = 'cur';
            }
            _self.seek(no, type);
        },
        getCurrent: function () {
            var _self = this;
            var $ele = _self.$ele;
            var liList = _self.liList;
            var $cur = $ele.find('.step-cur').parent();
            if ($cur.length == 0) {
                $cur = $ele.find('.step-sub-done').parent();
            }
            if ($cur.length == 0) {
                var $done = $ele.find('.step-done');
                $cur = $($done.get($done.length - 1)).parent();

            }
            if ($cur.length == 0) {
                $cur = liList[liList.length - 1];
            }
            _self.getEleAttach($cur);
            return $cur;
        },
        clear: function () {
            var _self = this;
            var $ele = _self.$ele;
            var liList = _self.liList;
            //清除样式
            for (var i = 0; i < liList.length; i++) {
                var $item = liList[i];
                $item.div.removeClass();
                _self.setNo($item);
            }
            liList[0].find('>div').addClass('step-cur');

        },
        seek: function (index, type) {
            var _self = this;
            var $ele = _self.$ele;
            var liList = _self.liList;

            var no = parseInt(index);
            if (index > liList.length || no <= 0) {
                return;
            }
            _self.clear();
            for (var i = 0; i < liList.length && i < no; i++) {
                var $item = liList[i];
                $item.div.removeClass();
                $item.div.addClass('step-done');
                _self.clearNo($item);
            }
            switch (type) {
                case 'cur':
                    liList[no - 1].div.removeClass();
                    liList[no - 1].div.addClass('step-cur');
                    _self.setNo($item);
                    break;
                default:

            }
            if (no == liList.length) {
                liList[liList.length - 1].div.removeClass();
                liList[liList.length - 1].div.addClass('step-cur');
                _self.setNo($item);
            }
        },


        unbind: function () {
            return this;
        }
    });
    return FlowStep;
});
