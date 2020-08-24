package org.equinoxosgi.toast.internal.dev.airbag.fake;

import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private FakeAirbag airbag;
	private ServiceRegistration registration;

	@Override
	public void start(BundleContext context) throws Exception {
		FakeAirbag airbag = new FakeAirbag();
		airbag.startup();
		registration = context.registerService(IAirbag.class.getName(), airbag, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		airbag.shutdown();
	}

}
