package catalago.core.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageLocale implements MessageLocaleComponent {

	@Override
	public String validationMessageSource(String keyMessage, Object... objects) {
		MessageSource messageSource = MessageSourceFactory
				.createMessageSource("classpath:/messages/validation/message");
		return getMessage(messageSource, keyMessage, objects);
	}

	private String getMessage(final MessageSource messageSource, final String key, final Object... params) {
		if (key == null) {
			log.error("Messages Key can't be null!");
			return null;
		}

		final String msg;
		if (params != null && params.length > 0) {
			msg = messageSource.getMessage(key.replaceAll("[{}]", ""), params, Locale.getDefault());
		} else {
			msg = messageSource.getMessage(key.replaceAll("[{}]", ""), (Object[]) null, Locale.getDefault());
		}
		if (key.equals(msg)) {
			log.error("Messages Key wasn't found! KEY: " + key);
		}
		return msg;
	}

}