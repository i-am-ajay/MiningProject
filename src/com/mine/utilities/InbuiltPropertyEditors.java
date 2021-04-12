package com.mine.utilities;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

public class InbuiltPropertyEditors implements PropertyEditorRegistrar {

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
