
/*
 * generated by Xtext
 */
 
package org.eclipse.emf.mwe2.language.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 *@generated
 */
public class Mwe2ExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return org.eclipse.emf.mwe2.language.ui.internal.Mwe2Activator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return org.eclipse.emf.mwe2.language.ui.internal.Mwe2Activator.getInstance().getInjector("org.eclipse.emf.mwe2.language.Mwe2");
	}
	
}