package org.equinoxosgi.toast.internal.dev.airbag.fake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.airbag.IAirbagListener;

public class FakeAirbag implements IAirbag {

	private List listeners = new ArrayList<>();
	private Job job;
	private boolean isRunning;

	@Override
	public synchronized void addListener(IAirbagListener listener) {
		listeners.add(listener);
	}

	@Override
	public synchronized void removeListener(IAirbagListener listener) {
		listeners.remove(listener);
	}

	@Override
	public synchronized void deploy() {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
			((IAirbagListener) i.next()).deployed();
		}
	}

	public synchronized void shutdown() {
		isRunning = false;
		job.cancel();
		try {
			// The join call in the shutdown method is necessary to ensure that
			// we wait for the Jobto terminate. To comply with the OSGi
			// specification, a bundle being stopped must ensure that it
			// disposes of all consumed system resources.
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void startup() {
		isRunning = true;
		job = new Job("FakeAirbag") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				deploy();
				if (isRunning)
					schedule(5000);
				return Status.OK_STATUS;
			}
		};
		job.schedule(5000);
	}
}
