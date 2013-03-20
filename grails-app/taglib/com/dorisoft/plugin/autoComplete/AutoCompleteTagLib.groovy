package com.dorisoft.plugin.autoComplete

class AutoCompleteTagLib {
	static namespace = "autoComplete"


	def autoCompleteBootstrap = {attrs ->

		def expectedParams = ['max' , 'value' , 'order' , 'collectField' , 'name' ,
		'id' , 'controller' , 'action' , 'domain'  ]
		
		def unExpectedParams = []
		attrs.findAll { !(it.key in expectedParams)}.each {unExpectedParams<< "${it.key}='${it.value}'"}

		if (attrs.id == null)
		throwTagError("Tag [autoComplete] is missing required attribute [id]")

		if (attrs.domain == null)
		throwTagError("Tag [autoComplete] is missing required attribute [domain]")

		if (attrs.searchField == null)
		throwTagError("Tag [autoComplete] is missing required attribute [searchField]")


		def name = ""
		if (!attrs.max) attrs.max = 10
		if (!attrs.value) attrs.value =""
		if (!attrs.order) attrs.order = "asc"
		if (!attrs.collectField) attrs.collectField = attrs.searchField

		if (attrs.name)
		name = " name ='${attrs.name}'"
		else
		name = " name ='${attrs.id}'"

		def link = createLink(['action': attrs.action?:'autoComplete' , 'controller': attrs.controller?:'multiPurpose' ])
		link += "?"
		link += "&domain="+ attrs.domain
		link += "&searchField="+attrs.searchField
		link += "&max="+attrs.max
		link += "&order="+attrs.order
		link += "&collectField="+attrs.collectField

		out << autoCompleteHeader()
		out << " <input  type='text' ${unExpectedParams.join (' ')} id='${attrs.id}' value = '${attrs.value}' ${name} autocomplete='off'/>"
		out << "<script type='text/javascript'>"
		out << """
			\$('#${attrs.id}').ajaxStart(function() {
				\$(this).addClass('ui-autocomplete-loading');
			}).ajaxStop(function() {
				\$(this).removeClass('ui-autocomplete-loading');
			});		
		"""
		out << " \$(document).ready(function() {"
		out << "	\$('#" + attrs.id+"').typeahead({ "
		out << "		source: function (query, process) {"
		out << "			\$.get('$link', { q: query }, function (data) {"
		out << " 			process(data); "
		out << "		})"
		out << "		}"
		out << "	})"
		out << "})"
		out << "</script>"
}


def autoCompleteHeader = {
	def url = createLinkTo (dir: "$pluginContextPath/images",file:'spinner.gif')
	out << "<style>"
	out << 	".ui-autocomplete-loading"
	out << "	{ background: white url('$url') right center no-repeat   }"
	out << " </style>"
}

}
