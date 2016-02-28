var $simon = {
	operator : "",
	path : "",
	get : function(o) {
		var ajax = Object.create(Ajax);
		return ajax.doAjax(o, "GET", this.operator);
	},
	put : function(o) {
		var ajax = Object.create(Ajax);
		return ajax.doAjax(o, "PUT", this.operator);
	},
	post : function(o) {
		var ajax = Object.create(Ajax);
		return ajax.doAjax(o, "POST", this.operator);
	},
	del : function(o) {
		var ajax = Object.create(Ajax);
		return ajax.doAjax(o, "DELETE", this.operator);
	},
	getQueryVariable : function getQueryVariable(variable) {
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i = 0; i < vars.length; i++) {
			var pair = vars[i].split("=");
			if (pair[0] == variable) {
				return pair[1];
			}
		}
		return "";
	}
};

var Ajax = {
	url : "",
	type : "",
	async : false,
	data : {},
	dataType : "",
	contentType : "",
	error : function() {

	},
	doAjax : function(o, type, operator) {
		var _result;

		this.url = o.url;

		if (o != null && !isEmpty(o.async)) {
			this.async = o.async;
		}
		if (o != null && !isEmpty(o.error)) {
			this.error = o.error;
		}
		if (o != null && !isEmpty(o.dataType)) {
			this.dataType = o.dataType;
		}
		if (o != null && !isEmpty(o.contentType)) {
			this.contentType = o.contentType;
		}
		if (!isEmpty(o.data)) {
			this.data = o.data;
		}

		if (type == "POST") {
			this.type = "POST";
		} else if (type == "GET") {
			this.type = "GET";
		} else if (type == "PUT") {
			this.type = "POST";
			this.data._method = "PUT";
		} else if (type == "DELETE") {
			this.type = "POST";
			this.data._method = "DELETE";
		}

		$.ajax({
			url : this.url,
			type : this.type,
			async : this.async,
			data : this.data,
			dataType : this.dataType,
			success : function(data) {
				_result = data;
				if (o != null && !isEmpty(o.success)) {
					o.success(_result);
				}
			},
			error : this.error
		});

		return _result;
	}
};

function isEmpty(o) {
	if (typeof o == 'undefine' || o == null || o == "") {
		return true;
	} else {
		return false;
	}
}
