package catalago.core.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public abstract class MessageSourceFactory {
	public MessageSourceFactory() {
	}

	public static MessageSource createMessageSource(String classpath) {
		return createMessageSource(classpath, "UTF-8");
	}

	public static MessageSource createMessageSource(String classpath, String encoding) {
		Locale.setDefault(new Locale("pt", "BR"));
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(classpath);
		messageSource.setDefaultEncoding(encoding);
		return messageSource;
	}
}