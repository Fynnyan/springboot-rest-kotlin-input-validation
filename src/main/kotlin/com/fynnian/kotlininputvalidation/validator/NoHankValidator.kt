package com.fynnian.kotlininputvalidation.validator

import org.springframework.util.StringUtils
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass


@Constraint(validatedBy = [NoHankValidator::class])
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class NoHankValidation(
    val message: String = "No Hank!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []) {
}

class NoHankValidator : ConstraintValidator<NoHankValidation, String> {

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    context.disableDefaultConstraintViolation()

    if (StringUtils.hasText(value)) {
      if (value!!.contains("Hank", true)) {
        context.buildConstraintViolationWithTemplate("Hank is not allowed!").addConstraintViolation()
        return false;
      }
    }
    return true;
  }
}


annotation class ConsistentDateParameters()