package org.eclipse.emf.mwe2.launch.shortcut;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

public class Mwe2LaunchDelegate extends JavaLaunchDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (RefreshTab.getRefreshScope(configuration) != null) {
			DebugPlugin.getDefault().addDebugEventListener(new IDebugEventSetListener() {
				public void handleDebugEvents(DebugEvent[] events) {
					for (int i = 0; i < events.length; i++) {
						DebugEvent event = events[i];
						if (event.getSource() instanceof IProcess && event.getKind() == DebugEvent.TERMINATE) {
							IProcess process = (IProcess) event.getSource();
							if (configuration == process.getLaunch().getLaunchConfiguration()) {
								DebugPlugin.getDefault().removeDebugEventListener(this);
								Job job = new Job("Refreshing after mwe2 lauch") {
									public IStatus run(IProgressMonitor monitor) {
										try {
											RefreshTab.refreshResources(configuration, monitor);
										} catch (CoreException e) {
											// ExternalToolsPlugin.getDefault().log(e);
											return e.getStatus();
										}
										return Status.OK_STATUS;
									}
								};
								job.schedule();
								return;
							}
						}
					}
				}
			});
		}
		super.launch(configuration, mode, launch, monitor);
	}

}