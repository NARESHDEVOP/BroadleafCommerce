package org.broadleafcommerce.openadmin.server.service.sandbox;

import org.broadleafcommerce.common.sandbox.service.SandBoxService;
import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidationResult;
import org.broadleafcommerce.openadmin.server.service.persistence.validation.ValidationConfigurationBasedPropertyValidator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Ensures that the SandBox name is unique within a given site.
 * 
 * @author bpolster
 */
@Component("blSandBoxNameValidator")
public class SandBoxNameValidator extends ValidationConfigurationBasedPropertyValidator {

    @Resource(name = "blSandBoxService")
    protected SandBoxService sandboxService;

    /**
     * Denotes what should occur when this validator encounters a null value to validate against. Default behavior is to
     * allow them, which means that this validator will always return true with null values
     */
    protected boolean succeedForNullValues = false;

    protected String ERROR_DUPLICATE_SANDBOX_NAME = "errorDuplicateSandBoxName";

    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {

        if (value == null) {
            return new PropertyValidationResult(succeedForNullValues);
        }

        if (!sandboxService.checkForExistingApprovalSandboxWithName(value)) {
            return new PropertyValidationResult(false, ERROR_DUPLICATE_SANDBOX_NAME);
        }

        return new PropertyValidationResult(true);
    }

    public boolean isSucceedForNullValues() {
        return succeedForNullValues;
    }

    public void setSucceedForNullValues(boolean succeedForNullValues) {
        this.succeedForNullValues = succeedForNullValues;
    }

}