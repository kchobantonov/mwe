package org.eclipse.emf.mwe2.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.mwe2.language.factory.Mwe2ExecutionEngine;
import org.eclipse.emf.mwe2.language.mwe2.Module;
import org.eclipse.emf.mwe2.language.mwe2.Mwe2Package;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflow;
import org.eclipse.emf.mwe2.runtime.workflow.WorkflowContextImpl;
import org.eclipse.xtext.mwe.RuntimeResourceSetInitializer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

public class MweRunner {
	
	@Inject
	private RuntimeResourceSetInitializer initializer;
	
	@Inject
	private Mwe2ExecutionEngine engine;
	
	public void run(String moduleName, Map<String, String> params) {
		Module module = findModule(moduleName);
		if (module==null)
			throw new IllegalArgumentException("Couldn't find module with name '"+moduleName+"'.");
		Map<String,Object> actualParams = getRealParams(params);
		Object object = engine.create(module, actualParams);
		if (!(object instanceof IWorkflow)) {
			throw new IllegalArgumentException("The root element must be of type IWorkflow but was '"+object.getClass()+"'.");
		}
		((IWorkflow)object).run(new WorkflowContextImpl());
	}

	protected Map<String, Object> getRealParams(Map<String, String> params) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.putAll(params);
		return map;
	}

	protected Module findModule(String moduleName) {
		ResourceSet resourceSet = initializer.getInitializedResourceSet(getPathes());
		IResourceDescriptions descriptions = initializer.getDescriptions(resourceSet);
		for (IResourceDescription desc : descriptions.getAllResourceDescriptions()) {
			Iterable<IEObjectDescription> iterable = desc.getExportedObjects(Mwe2Package.Literals.MODULE, moduleName);
			for (IEObjectDescription objDesc : iterable) {
				return (Module) resourceSet.getEObject(objDesc.getEObjectURI(), true);
			}
		}
		return null;
	}

	protected List<String> getPathes() {
		return initializer.getClassPathEntries();
	}
	
	public void setEngine(Mwe2ExecutionEngine engine) {
		this.engine = engine;
	}
	
	public void setInitializer(RuntimeResourceSetInitializer initializer) {
		this.initializer = initializer;
	}

}