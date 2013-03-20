package com.dorisoft.plugin.autoComplete

/**
 * DomainService
 * Purpose: 
 *
 * @author Ali Soleimani
 * @version 1.0 08/01/2012
 */
 import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
 import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod
 import org.codehaus.groovy.grails.commons.metaclass.GroovyDynamicMethodsInterceptor
 import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

 class DomainService {

 	def grailsApplication

  	DomainService(){
 		GroovyDynamicMethodsInterceptor i = new GroovyDynamicMethodsInterceptor(this)        
 		i.addDynamicMethodInvocation(new BindDynamicMethod()) 		
 	}

 	def typeConvertor(instanceObj,bindParams){
 		bindData(instanceObj, bindParams)
 		println "done"
 		instanceObj
 	}

 	def searchInList(list,packageName){
 		for (keyword in list) {
 			if (packageName.contains(keyword)) return true
 		}
 		return false
 	}

 	def listDomains(def includePackageList) {
 		def dlist = grailsApplication.getArtefacts("Domain")
 		def filteredList= dlist.findAll{ searchInList(includePackageList,it.getPackageName()) }
 		def dd = filteredList.name.clone()
 		dd.remove ('StgSampleInterface') 
 		return dd
 	}

 	def grailsArtifacts(name='Domain') {
 		def dlist = grailsApplication.getArtefacts(name)
 		return filteredList.name.clone()
 	} 

 	Class findDomainClass(String className){
 		grailsApplication.domainClasses.find { it.clazz.simpleName == className }?.clazz
 	}
 }
