/*
 * Copyright (c) 2008 committers of openArchitectureWare and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    committers of openArchitectureWare - initial API and implementation
 */

package org.eclipse.emf.mwe.ui.internal.editor.analyzer.references;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.mwe.ui.internal.editor.elements.WorkflowElement;
import org.eclipse.jface.text.IDocument;

/**
 * @author Patrick Schoenbach
 * @version $Revision: 1.2 $
 */
public abstract class AbstractReferenceAnalyzerStrategy implements
		IReferenceAnalyzerStrategy {

	protected ReferenceInfoStore store;

	protected final IDocument document;

	protected final IFile file;

	public AbstractReferenceAnalyzerStrategy(final IFile file,
			final IDocument document, final ReferenceInfoStore store) {
		this.file = file;
		this.document = document;
		if (store == null) {
			this.store = new ReferenceInfoStore(file);
		} else {
			this.store = store;
		}
	}

	/**
	 * This method overrides the implementation of <code>analyzeElement</code>
	 * inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.ui.internal.editor.analyzer.references.IReferenceAnalyzerStrategy#analyzeElement(org.eclipse.emf.mwe.ui.internal.editor.elements.WorkflowElement)
	 */
	public void analyzeElement(final WorkflowElement element) {
		if (element == null || !isApplicable(element))
			throw new IllegalArgumentException();

		doAnalyze(element);
	}

	/**
	 * This method overrides the implementation of <code>clearCache</code>
	 * inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.ui.internal.editor.analyzer.references.IReferenceAnalyzerStrategy#clearCache()
	 */
	public void clearCache() {
		if (store != null) {
			store.clear();
		}
	}

	/**
	 * This method overrides the implementation of
	 * <code>setReferenceInfoStore</code> inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.ui.internal.editor.analyzer.references.IReferenceAnalyzerStrategy#setReferenceInfoStore(org.eclipse.emf.mwe.ui.internal.editor.analyzer.references.ReferenceInfoStore)
	 */
	public void setReferenceInfoStore(final ReferenceInfoStore store) {
		if (store == null)
			throw new IllegalArgumentException();

		this.store = store;
	}

	/**
	 * Internal method performing the analyze.
	 * 
	 * @param element
	 *            the element.
	 */
	protected abstract void doAnalyze(WorkflowElement element);

}