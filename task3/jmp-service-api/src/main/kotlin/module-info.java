open module jmp.service.api {
    requires kotlin.stdlib;
    requires jmp.dto;
    requires spring.context;

    exports com.epam.sp.services to jmp.cloud.service.impl, jmp.service.rest;
    exports com.epam.sp.services.exceptions;
}