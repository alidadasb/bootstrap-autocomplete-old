package com.dorisoft.plugin.autoComplete

/**
 * @artifact.name@
 * Purpose: This controller is to collect all the actions that are not 
 * belong to any particular controller.
 *
 * @author Ali Soleimani
 * @version 1.0 08/01/2012
 */

 import grails.converters.JSON

 class MultiPurposeController {
 	def autoCompleteService
 	def domainService

	def autoComplete = {
 		log.debug params
 		Thread.sleep(1000)
 		render autoCompleteService.autocompleteDomains(params)
 	}

    def packagesAutoComplete = {
 		log.debug params    	
        def dd = domainService.listDomains([params.packageName])
        def ss = dd.collect {utilitiesService.toSnakeCase( it  )}
        render ss as JSON
    } 	

 }