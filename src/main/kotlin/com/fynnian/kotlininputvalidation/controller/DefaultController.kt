package com.fynnian.kotlininputvalidation.controller

import com.fynnian.kotlininputvalidation.validator.NoHankValidation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping("/api")
class DefaultController {

  @GetMapping("/greeter")
  fun greet(@RequestParam @NoHankValidation name: String): SimpleResponse {
    return SimpleResponse("Hi $name")
  }

  @PostMapping("/simple")
  fun updateSimple(@RequestBody @Valid request: SimpleRequest): SimpleResponse {
    return request.toResponse();
  }
}

data class SimpleRequest(
    @get: NotBlank(message = "The first name must be set")
    @get: NoHankValidation
    val firstName: String = "",
    @get: NotBlank(message = "The last name must be set")
    val lastName: String = "") {
  fun toResponse(): SimpleResponse = SimpleResponse("Hello $firstName $lastName")
}

data class SimpleResponse(val result: String)