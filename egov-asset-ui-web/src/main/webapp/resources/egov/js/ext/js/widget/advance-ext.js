define(function() {

	function AdvanceSearch(config) {
		if (config && config.id) {
			var _self = this;
			_self.id = config.id;
			$('#' + this.id + ' .queryadv>a').remove();
			_self.content = $('#' + _self.id + ' .queryadv>input').val();
			$('#' + _self.id + ' .queryadv').append(
					'<span class="__advance_btn" >高级</span>');

			$('#' + _self.id + ' .queryadv>span').on('click', function() {
				_self.popup();
			});
		}
	};
	
	AdvanceSearch.prototype = {
		popup : function() {
			var _self = this;
			var adDialog = '<div class="__popover __fade in" id="popover463253">'
					+
					//'<div class="arrow" style="top: 50%;"></div>'+
					//'<h3 class="popover-title">Popover title</h3>'+
					'<div class="__popover-content">'
					+ _self.content
					+ '</div>' + '</div>';
			var $btn = $('#' + _self.id);
			var contentOffset = $('#' + _self.id + ' .queryadv>span').offset();
			contentOffset.top += 20;
			//contentOffset.left += 20;
			if ($btn.length > 0) {
				$('body').append(adDialog);
				$('#popover463253').show();
				$('#popover463253').offset(contentOffset);
				$('#_dialogMask').show();
				$('#_dialogMask').on('click.custom.advance', function() {
					$('#popover463253').remove();
					$('#_dialogMask').off('click.custom.advance');
					$('#_dialogMask').hide();
				});
			}
		}
	}
	return AdvanceSearch;
});
